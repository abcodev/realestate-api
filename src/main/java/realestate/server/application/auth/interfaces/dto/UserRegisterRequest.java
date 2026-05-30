package realestate.server.application.auth.interfaces.dto;

import jakarta.validation.constraints.NotBlank;

public record UserRegisterRequest(
        @NotBlank String accessToken,
        @NotBlank String nickname,
        String authorizationCode,
        String idToken,
        String bio,
        String pushEnabled) {
}

