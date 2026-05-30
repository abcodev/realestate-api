package realestate.server.application.realestate.infrastructure.jpa.repository;

import realestate.server.application.realestate.infrastructure.jpa.entity.SggCodeJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface SggCodeJpaRepository extends JpaRepository<SggCodeJpaEntity, Long> {

    Optional<SggCodeJpaEntity> findBySggCd(String sggCd);

}
