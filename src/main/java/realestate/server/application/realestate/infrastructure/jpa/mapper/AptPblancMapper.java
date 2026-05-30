package realestate.server.application.realestate.infrastructure.jpa.mapper;

import realestate.server.application.realestate.domain.AptPblanc;
import realestate.server.application.realestate.infrastructure.jpa.entity.AptPblancJpaEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AptPblancMapper {
    AptPblancJpaEntity toEntity(AptPblanc domain);
    AptPblanc toDomain(AptPblancJpaEntity entity);
}
