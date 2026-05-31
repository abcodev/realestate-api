package realtyos.server.application.user.application;

import realtyos.server.application.user.domain.User;
import realtyos.server.application.user.domain.UserRepository;
import realtyos.server.application.user.domain.UserType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {

    private final UserRepository userRepository;

    @Transactional
    public void syncUserType(Long userId, UserType targetType) {
        if (targetType == null || targetType == UserType.GENERAL) {
            return;
        }

        User user = userRepository.fetchBy(userId);

        if (user.userType() == null || user.userType() == UserType.GENERAL) {
            userRepository.updateUserType(userId, targetType);
            log.info("유저 타입 동기화 완료 - userId: {}, targetType: {}", userId, targetType);
        }
    }
}
