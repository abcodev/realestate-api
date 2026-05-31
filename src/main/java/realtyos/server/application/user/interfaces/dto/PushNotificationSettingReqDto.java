package realtyos.server.application.user.interfaces.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PushNotificationSettingReqDto {

    @NotNull(message = "푸시 알림 수신 여부를 입력해주세요.")
    private Boolean pushEnabled;
}
