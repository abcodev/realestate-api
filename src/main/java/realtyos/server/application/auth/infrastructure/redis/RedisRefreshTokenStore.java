package realtyos.server.application.auth.infrastructure.redis;

import realtyos.server.application.auth.domain.RefreshTokenStore;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.concurrent.TimeUnit;

@Slf4j
@Component
@RequiredArgsConstructor
public class RedisRefreshTokenStore implements RefreshTokenStore {

    private static final String KEY_PREFIX = "refresh_token:";

    private final RedisTemplate<String, String> redisTemplate;

    @Override
    public void save(Long userId, String refreshToken, long ttlSeconds) {
        String key = KEY_PREFIX + userId;
        redisTemplate.opsForValue().set(key, refreshToken, ttlSeconds, TimeUnit.SECONDS);
        log.debug("Refresh token 저장 - userId: {}, ttl: {}s", userId, ttlSeconds);
    }

    @Override
    public Optional<String> find(Long userId) {
        String key = KEY_PREFIX + userId;
        String token = redisTemplate.opsForValue().get(key);
        return Optional.ofNullable(token);
    }

    @Override
    public void delete(Long userId) {
        String key = KEY_PREFIX + userId;
        redisTemplate.delete(key);
        log.debug("Refresh token 삭제 - userId: {}", userId);
    }
}
