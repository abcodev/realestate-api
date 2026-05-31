package realtyos.server.application.auth.interfaces.dto;

public record OAuthUserInfo(
        String providerId,
        String email,
        String name) {
}
