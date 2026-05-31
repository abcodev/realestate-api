package realtyos.server.application.auth.infrastructure.jpa;

import realtyos.server.application.auth.domain.LoginHistory;
import realtyos.server.application.auth.domain.LoginHistoryRepository;
import realtyos.server.application.auth.infrastructure.jpa.entity.LoginHistoryJpaEntity;
import realtyos.server.application.auth.infrastructure.jpa.repository.LoginHistoryJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class LoginHistoryRepositoryJpaAdaptor implements LoginHistoryRepository {

    private final LoginHistoryJpaRepository jpaRepository;

    @Override
    public void save(LoginHistory loginHistory) {
        jpaRepository.save(LoginHistoryJpaEntity.builder()
                .userId(loginHistory.userId())
                .loginIp(loginHistory.loginIp())
                .loginAt(loginHistory.loginAt())
                .build());
    }
}
