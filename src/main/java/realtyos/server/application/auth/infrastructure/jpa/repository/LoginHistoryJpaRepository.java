package realtyos.server.application.auth.infrastructure.jpa.repository;

import realtyos.server.application.auth.infrastructure.jpa.entity.LoginHistoryJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LoginHistoryJpaRepository extends JpaRepository<LoginHistoryJpaEntity, Long> {
}
