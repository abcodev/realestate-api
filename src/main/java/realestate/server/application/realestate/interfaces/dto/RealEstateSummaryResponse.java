package realestate.server.application.realestate.interfaces.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

@Schema(description = "부동산 관심사 응답 DTO")
public record RealEstateSummaryResponse(
        @Schema(description = "관심사 설정 지역")
        String region,
        @Schema(description = "부동산 실거래 관심 목록")
        List<DealsSummaryResponse> deals,
        @Schema(description = "아파트 공고 관심 목록")
        List<AptPblancSummaryResponse> aptPblancs,
        @Schema(description = "임대 공고 관심 목록")
        List<RentPblancSummaryResponse> rentPblancs,
        @Schema(description = "부동산 일간 주요 뉴스 요약")
        RealEstateNewsSummaryDto newsSummary) {
}
