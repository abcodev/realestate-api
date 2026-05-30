package realestate.server.application.realestate.interfaces.dto;

import realestate.server.application.realestate.domain.RealEstateNewsSummary;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDate;

@Schema(description = "부동산 AI 뉴스 요약 응답 DTO")
public record RealEstateNewsSummaryDto(
        @Schema(description = "요약 기준일")
        LocalDate summaryDate,
        @Schema(description = "부동산 뉴스 1줄 요약")
        String oneLineBrief,
        @Schema(description = "부동산 뉴스 상세 요약")
        String summary
) {
    public static RealEstateNewsSummaryDto from(RealEstateNewsSummary domain) {
        if (domain == null) return null;
        return new RealEstateNewsSummaryDto(
                domain.summaryDate(),
                domain.oneLineBrief(),
                domain.summary()
        );
    }
}
