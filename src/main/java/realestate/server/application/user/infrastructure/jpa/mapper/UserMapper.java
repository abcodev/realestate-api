package realestate.server.application.user.infrastructure.jpa.mapper;

import realestate.server.application.user.domain.User;
import realestate.server.application.user.infrastructure.jpa.entity.UserJpaEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {

    User toDomain(UserJpaEntity entity);

    UserJpaEntity toEntity(User domain);
}
