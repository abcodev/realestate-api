package realtyos.server.application.auth.domain;

import java.util.Optional;

public interface RefreshTokenStore {

    void save(Long userId, String refreshToken, long ttlSeconds);

    Optional<String> find(Long userId);

    void delete(Long userId);
}
