package realestate.server.application.realestate.infrastructure.jpa.repository;

import realestate.server.application.realestate.infrastructure.jpa.entity.AptPblancJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


import java.util.List;

public interface AptPblancJpaRepository extends JpaRepository<AptPblancJpaEntity, Long> {

    boolean existsByPblancNo(String pblancNo);

    List<AptPblancJpaEntity> findAllBySggCode(String sggCode);

    @Query("SELECT a FROM AptPblancJpaEntity a " +
            "WHERE a.sggCode = :sggCode " +
            "AND a.rceptBgnde >= :sevenDaysAgo " +
            "AND a.rceptEndde >= :today")
    List<AptPblancJpaEntity> findValidApts(
            @Param("sggCode") String sggCode,
            @Param("sevenDaysAgo") String sevenDaysAgo,
            @Param("today") String today
    );

}
