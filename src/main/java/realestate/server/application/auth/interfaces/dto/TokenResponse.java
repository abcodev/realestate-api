package realestate.server.application.auth.interfaces.dto;

public record TokenResponse(
        String accessToken,
        String refreshToken) {
}

