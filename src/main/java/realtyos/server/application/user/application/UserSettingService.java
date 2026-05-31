package realtyos.server.application.user.application;

import realtyos.server.application.user.domain.User;
import realtyos.server.application.user.domain.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserSettingService {

    private final UserRepository userRepository;

    @Transactional(readOnly = true)
    public Map<String, Object> getNotificationSettings(Long userId) {
        User user = userRepository.fetchBy(userId);
        return Map.of(
                "pushEnabled", "Y".equalsIgnoreCase(user.pushEnabled()),
                "briefingPushTime", user.briefingPushTime() != null ? user.briefingPushTime() : "08:00"
        );
    }

    @Transactional
    public void updatePushNotification(Long userId, boolean pushEnabled) {
        // 유저 존재 여부 확인 및 push_enabled 업데이트
        userRepository.updatePushEnabled(userId, pushEnabled);

        log.info("푸시 알림 설정 변경 - userId: {}, pushEnabled: {}", userId, pushEnabled);
    }

    @Transactional
    public void updateLocationConsent(Long userId, boolean locationConsentEnabled) {
        userRepository.updateLocationConsentEnabled(userId, locationConsentEnabled);

        log.info("위치 이용 동의 설정 변경 - userId: {}, locationConsentEnabled: {}", userId, locationConsentEnabled);
    }

    @Transactional
    public void updateBriefingPushTime(Long userId, String time) {
        userRepository.updateBriefingPushTime(userId, time);
        log.info("브리핑 푸시 시간 설정 변경 - userId: {}, time: {}", userId, time);
    }
}
