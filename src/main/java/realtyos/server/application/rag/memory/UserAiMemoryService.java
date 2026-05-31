package realtyos.server.application.rag.memory;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import realtyos.server.application.rag.domain.RagQueryRewritePolicy;
import realtyos.server.application.rag.domain.RagSearchCondition;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserAiMemoryService {

    private static final int RECENT_EVENT_AGGREGATION_SIZE = 50;

    private final UserAiMemoryJpaRepository repository;
    private final UserAiMemoryEventJpaRepository eventRepository;
    private final RagQueryRewritePolicy queryRewritePolicy = new RagQueryRewritePolicy();

    @Transactional(readOnly = true)
    public Optional<UserAiMemory> find(Long userId) {
        if (userId == null) {
            return Optional.empty();
        }
        return repository.findByUserId(userId)
                .map(UserAiMemoryJpaEntity::toDomain);
    }

    public RagSearchCondition merge(Long userId, String query, RagSearchCondition condition) {
        RagSearchCondition inferredCondition = queryRewritePolicy.rewrite(query, condition).condition();
        return find(userId)
                .map(memory -> memory.mergeInto(inferredCondition))
                .orElse(inferredCondition);
    }

    @Transactional
    public void record(Long userId, String query, RagSearchCondition condition) {
        if (userId == null) {
            return;
        }
        RagSearchCondition inferredCondition = queryRewritePolicy.rewrite(query, condition).condition();
        eventRepository.save(UserAiMemoryEventJpaEntity.create(userId, query, inferredCondition));
        String frequentRegion = findFrequentRegion(userId).orElse(inferredCondition.region());
        UserAiMemoryJpaEntity entity = repository.findByUserId(userId)
                .orElseGet(() -> UserAiMemoryJpaEntity.create(userId));
        entity.record(query, inferredCondition, frequentRegion);
        repository.save(entity);
    }

    @Transactional(readOnly = true)
    public List<UserAiMemoryEventJpaEntity> findEvents(Long userId, Integer limit) {
        if (userId == null) {
            return List.of();
        }
        return eventRepository.findByUserIdOrderByCreatedAtDesc(
                userId,
                PageRequest.of(0, normalizeLimit(limit))
        );
    }

    @Transactional
    public UserAiMemory updatePreference(Long userId, String preferredRegion, Long minPrice, Long maxPrice) {
        UserAiMemoryJpaEntity entity = repository.findByUserId(userId)
                .orElseGet(() -> UserAiMemoryJpaEntity.create(userId));
        entity.updatePreference(preferredRegion, minPrice, maxPrice);
        return repository.save(entity).toDomain();
    }

    @Transactional
    public void clear(Long userId) {
        eventRepository.deleteByUserId(userId);
        repository.deleteByUserId(userId);
    }

    private Optional<String> findFrequentRegion(Long userId) {
        return eventRepository.findByUserIdAndRegionIsNotNullOrderByCreatedAtDesc(
                        userId,
                        PageRequest.of(0, RECENT_EVENT_AGGREGATION_SIZE)
                )
                .stream()
                .collect(Collectors.groupingBy(
                        UserAiMemoryEventJpaEntity::getRegion,
                        Collectors.counting()
                ))
                .entrySet()
                .stream()
                .max(Comparator
                        .comparingLong((Map.Entry<String, Long> entry) -> entry.getValue())
                        .thenComparing(Map.Entry::getKey))
                .map(Map.Entry::getKey);
    }

    private int normalizeLimit(Integer limit) {
        if (limit == null || limit <= 0) {
            return 20;
        }
        return Math.min(limit, 100);
    }
}
