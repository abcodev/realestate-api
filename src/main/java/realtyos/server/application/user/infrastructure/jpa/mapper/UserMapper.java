package realtyos.server.application.user.infrastructure.jpa.mapper;

import realtyos.server.application.user.domain.User;
import realtyos.server.application.user.infrastructure.jpa.entity.UserJpaEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {

    User toDomain(UserJpaEntity entity);

    UserJpaEntity toEntity(User domain);
}
