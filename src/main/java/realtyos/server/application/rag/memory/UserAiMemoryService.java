package realtyos.server.application.rag.memory;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import realtyos.server.application.rag.domain.RagQueryRewritePolicy;
import realtyos.server.application.rag.domain.RagSearchCondition;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserAiMemoryService {

    private final UserAiMemoryJpaRepository repository;
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
        UserAiMemoryJpaEntity entity = repository.findByUserId(userId)
                .orElseGet(() -> UserAiMemoryJpaEntity.create(userId));
        entity.record(query, inferredCondition);
        repository.save(entity);
    }
}
