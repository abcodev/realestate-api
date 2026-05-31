package realtyos.server.application.auth.interfaces.dto;

public record LoginRequest(
        String accessToken,
        String authorizationCode,
        String idToken,
        String redirectUri,
        String pushEnabled,
        String nickname) {
}
