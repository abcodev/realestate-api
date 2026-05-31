package realtyos.server.application.realestate.interfaces.dto;

import realtyos.server.application.realestate.domain.Deals;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "부동산 실거래 요약 응답 DTO")
public record DealsSummaryResponse(

        @Schema(description = "아파트 이름")
        String aptName,

        String dealAmount,
        Integer dealYear,
        Integer dealMonth,
        Integer dealDay,

        String exclusiveUseArea) {

    public static DealsSummaryResponse from(Deals deals) {
        return new DealsSummaryResponse(deals.aptName(), deals.dealAmount(), deals.dealYear(), deals.dealMonth(), deals.dealDay(), deals.excluUseArea());
    }

}
