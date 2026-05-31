package realtyos.server.application.realestate.infrastructure.jpa.repository;

import realtyos.server.application.realestate.infrastructure.jpa.entity.DealsJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DealsJpaRepository extends JpaRepository<DealsJpaEntity, Long> {

        List<DealsJpaEntity> findBySggCodeAndDealYearAndDealMonth(String sggCode, Integer dealYear, Integer dealMonth);
}
