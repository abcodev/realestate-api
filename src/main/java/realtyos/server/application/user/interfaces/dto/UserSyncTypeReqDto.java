package realtyos.server.application.user.interfaces.dto;

import realtyos.server.application.user.domain.UserType;
import io.swagger.v3.oas.annotations.media.Schema;

public record UserSyncTypeReqDto(
    @Schema(description = "동기화할 유저 타입 (예: NO_ADS)", example = "NO_ADS")
    UserType userType
) {}
