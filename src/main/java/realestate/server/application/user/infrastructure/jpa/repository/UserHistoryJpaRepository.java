package realestate.server.application.user.infrastructure.jpa.repository;

import realestate.server.application.user.infrastructure.jpa.entity.UserHistoryJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserHistoryJpaRepository extends JpaRepository<UserHistoryJpaEntity, Long> {
    Optional<UserHistoryJpaEntity> findByUserId(Long userId);
}
