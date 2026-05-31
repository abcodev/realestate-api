package realtyos.server.application.auth.domain;

import java.time.LocalDateTime;

/**
 * 로그인 접속 이력 도메인 모델.
 */
public record LoginHistory(
        Long id,
        Long userId,
        String loginIp,
        LocalDateTime loginAt
) {
    public static LoginHistory create(Long userId, String loginIp) {
        return new LoginHistory(null, userId, loginIp, LocalDateTime.now());
    }
}
