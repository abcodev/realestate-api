package realtyos.server.application.realestate.infrastructure.jpa.mapper;

import realtyos.server.application.realestate.domain.AptPblanc;
import realtyos.server.application.realestate.infrastructure.jpa.entity.AptPblancJpaEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AptPblancMapper {
    AptPblancJpaEntity toEntity(AptPblanc domain);
    AptPblanc toDomain(AptPblancJpaEntity entity);
}
