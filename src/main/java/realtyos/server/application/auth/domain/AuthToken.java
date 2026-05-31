package realtyos.server.application.auth.domain;

public record AuthToken(
        String accessToken,
        String refreshToken) {
}
