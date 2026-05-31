package realtyos.server.application.user.infrastructure.jpa.mapper;

import realtyos.server.application.user.domain.UserHistory;
import realtyos.server.application.user.infrastructure.jpa.entity.UserHistoryJpaEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserHistoryMapper {
    UserHistory toDomain(UserHistoryJpaEntity entity);
    UserHistoryJpaEntity toEntity(UserHistory domain);
}
