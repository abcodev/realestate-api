package realtyos.server.application.realestate.infrastructure.jpa.mapper;

import realtyos.server.application.realestate.domain.SggCode;
import realtyos.server.application.realestate.infrastructure.jpa.entity.SggCodeJpaEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface SggCodeMapper {

    SggCodeJpaEntity mapToJpaEntity(SggCode domain);

    SggCode mapToDomain(SggCodeJpaEntity entity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    void updateEntityFromDomain(SggCode domain, @MappingTarget SggCodeJpaEntity entity);
}
