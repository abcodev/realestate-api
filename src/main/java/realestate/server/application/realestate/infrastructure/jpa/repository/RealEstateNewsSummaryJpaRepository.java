package realestate.server.application.realestate.infrastructure.jpa.repository;

import realestate.server.application.realestate.infrastructure.jpa.entity.RealEstateNewsSummaryJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.Optional;

public interface RealEstateNewsSummaryJpaRepository extends JpaRepository<RealEstateNewsSummaryJpaEntity, Long> {
    
    Optional<RealEstateNewsSummaryJpaEntity> findTopByOrderBySummaryDateDesc();
    
    Optional<RealEstateNewsSummaryJpaEntity> findBySummaryDate(LocalDate summaryDate);
    
    @Modifying
    @Query("DELETE FROM RealEstateNewsSummaryJpaEntity e WHERE e.summaryDate = :summaryDate")
    void deleteBySummaryDate(LocalDate summaryDate);
}
