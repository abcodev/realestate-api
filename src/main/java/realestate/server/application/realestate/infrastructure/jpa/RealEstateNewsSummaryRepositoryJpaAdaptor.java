package realestate.server.application.realestate.infrastructure.jpa;

import realestate.server.application.realestate.domain.RealEstateNewsSummary;
import realestate.server.application.realestate.domain.RealEstateNewsSummaryRepository;
import realestate.server.application.realestate.infrastructure.jpa.entity.RealEstateNewsSummaryJpaEntity;
import realestate.server.application.realestate.infrastructure.jpa.mapper.RealEstateNewsSummaryMapper;
import realestate.server.application.realestate.infrastructure.jpa.repository.RealEstateNewsSummaryJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class RealEstateNewsSummaryRepositoryJpaAdaptor implements RealEstateNewsSummaryRepository {

    private final RealEstateNewsSummaryJpaRepository repository;
    private final RealEstateNewsSummaryMapper mapper;

    @Override
    public void save(RealEstateNewsSummary summary) {
        RealEstateNewsSummaryJpaEntity entity = mapper.toEntity(summary);
        repository.save(entity);
    }

    @Override
    public Optional<RealEstateNewsSummary> findLatest() {
        return repository.findTopByOrderBySummaryDateDesc().map(mapper::toDomain);
    }

    @Override
    public Optional<RealEstateNewsSummary> findBySummaryDate(LocalDate summaryDate) {
        return repository.findBySummaryDate(summaryDate).map(mapper::toDomain);
    }

    @Override
    public void deleteBySummaryDate(LocalDate summaryDate) {
        repository.deleteBySummaryDate(summaryDate);
    }
}
