package realestate.server.application.realestate.domain;

import java.time.LocalDate;
import java.util.Optional;

public interface RealEstateNewsSummaryRepository {
    void save(RealEstateNewsSummary summary);
    Optional<RealEstateNewsSummary> findLatest();
    Optional<RealEstateNewsSummary> findBySummaryDate(LocalDate summaryDate);
    void deleteBySummaryDate(LocalDate summaryDate);
}
