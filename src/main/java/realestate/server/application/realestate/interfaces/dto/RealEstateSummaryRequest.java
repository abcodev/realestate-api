package realestate.server.application.realestate.interfaces.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "부동산 실거래가 요약 요청 DTO")
public record RealEstateSummaryRequest(
        @Schema(description = "법정동 코드")
        String bgdCode
) {
}
