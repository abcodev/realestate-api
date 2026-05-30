package realestate.server.application.realestate.infrastructure.jpa.mapper;

import realestate.server.application.realestate.domain.Deals;
import realestate.server.application.realestate.infrastructure.jpa.entity.DealsJpaEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface DealsMapper {

    DealsJpaEntity mapToJpaEntity(Deals domain);

    Deals mapToDomain(DealsJpaEntity entity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    void updateEntityFromDomain(Deals domain, @MappingTarget DealsJpaEntity entity);
}
