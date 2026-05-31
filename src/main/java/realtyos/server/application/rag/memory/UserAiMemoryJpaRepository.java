package realtyos.server.application.rag.memory;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserAiMemoryJpaRepository extends JpaRepository<UserAiMemoryJpaEntity, Long> {

    Optional<UserAiMemoryJpaEntity> findByUserId(Long userId);

    void deleteByUserId(Long userId);
}
