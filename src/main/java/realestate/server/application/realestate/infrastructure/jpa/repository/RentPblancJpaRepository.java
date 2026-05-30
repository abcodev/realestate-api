package realestate.server.application.realestate.infrastructure.jpa.repository;

import realestate.server.application.realestate.infrastructure.jpa.entity.RentPblancJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface RentPblancJpaRepository extends JpaRepository<RentPblancJpaEntity, Long> {

    boolean existsByPblancNo(String pblancNo);

    List<RentPblancJpaEntity> findAllBySggCode(String sggCode);

    @Query("SELECT r FROM RentPblancJpaEntity r " +
            "WHERE r.sggCode = :sggCode " +
            "AND r.subscrptRceptBgnde >= :sevenDaysAgo " +
            "AND r.subscrptRceptEndde >= :today")
    List<RentPblancJpaEntity> findValidRents(
            String sggCode,
            String sevenDaysAgo,
            String today
    );

}
