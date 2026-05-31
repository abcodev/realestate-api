package realtyos.server.application.realestate.domain;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 부동산 관심 지역 도메인 엔티티.
 * 조회 전용 — 상태 변경은 Infrastructure(JPA Entity)에서 직접 처리합니다.
 */
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class InterestRegion {
    private Long id;
    private Long interestId;
    private String bgdCode;
    private String sggCode;
    private String umdCode;

    public static InterestRegion of(Long id, Long interestId, String bgdCode, String sggCode, String umdCode) {
        return new InterestRegion(id, interestId, bgdCode, sggCode, umdCode);
    }

    public RegionCode toRegionCode() {
        return new RegionCode(sggCode, umdCode);
    }
}
