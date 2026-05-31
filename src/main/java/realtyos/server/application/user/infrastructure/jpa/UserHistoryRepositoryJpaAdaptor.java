package realtyos.server.application.user.infrastructure.jpa;

import realtyos.server.application.user.domain.UserHistory;
import realtyos.server.application.user.domain.UserHistoryRepository;
import realtyos.server.application.user.infrastructure.jpa.entity.UserHistoryJpaEntity;
import realtyos.server.application.user.infrastructure.jpa.mapper.UserHistoryMapper;
import realtyos.server.application.user.infrastructure.jpa.repository.UserHistoryJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class UserHistoryRepositoryJpaAdaptor implements UserHistoryRepository {

    private final UserHistoryJpaRepository userHistoryJpaRepository;
    private final UserHistoryMapper userHistoryMapper;

    @Override
    public void save(UserHistory userHistory) {
        UserHistoryJpaEntity entity = userHistoryMapper.toEntity(userHistory);
        userHistoryJpaRepository.save(entity);
    }

    @Override
    public Optional<UserHistory> findByUserId(Long userId) {
        return userHistoryJpaRepository.findByUserId(userId)
                .map(userHistoryMapper::toDomain);
    }
}
