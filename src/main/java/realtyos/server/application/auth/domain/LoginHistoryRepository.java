package realtyos.server.application.auth.domain;

/**
 * 로그인 접속 이력 저장소 (도메인 포트).
 */
public interface LoginHistoryRepository {
    void save(LoginHistory loginHistory);
}
