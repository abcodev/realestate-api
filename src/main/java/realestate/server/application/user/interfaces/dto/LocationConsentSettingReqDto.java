package realestate.server.application.user.interfaces.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class LocationConsentSettingReqDto {

    @NotNull(message = "위치 이용 동의 여부를 입력해주세요.")
    private Boolean locationConsentEnabled;
}
