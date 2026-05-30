package realestate.server.application.realestate.infrastructure.jpa.mapper;

import realestate.server.application.realestate.domain.RealEstateNewsSummary;
import realestate.server.application.realestate.infrastructure.jpa.entity.RealEstateNewsSummaryJpaEntity;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface RealEstateNewsSummaryMapper {
    RealEstateNewsSummary toDomain(RealEstateNewsSummaryJpaEntity entity);
    RealEstateNewsSummaryJpaEntity toEntity(RealEstateNewsSummary domain);
}
