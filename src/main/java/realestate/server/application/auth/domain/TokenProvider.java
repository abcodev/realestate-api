package realestate.server.application.auth.domain;

public interface TokenProvider {
    AuthToken createToken(Long userId, String userType);

    boolean validateToken(String token);

    Long parseUserId(String token);

    String parseUserType(String token);
}

