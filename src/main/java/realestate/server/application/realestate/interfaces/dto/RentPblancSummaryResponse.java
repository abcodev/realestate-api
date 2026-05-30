package realestate.server.application.realestate.interfaces.dto;

import realestate.server.application.common.util.DDayUtils;
import realestate.server.application.realestate.domain.RentPblanc;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "임대공고 관심 정보 응답 DTO")
public record RentPblancSummaryResponse(
        @Schema(description = "ID")
        Long id,
        @Schema(description = "공급회사명")
        String companyName,
        @Schema(description = "공급유형")
        String type,
        @Schema(description = "주택명")
        String houseName,
        @Schema(description = "주소")
        String address,
        @Schema(description = "지역코드")
        String rentAreaCode,
        @Schema(description = "지역명")
        String rentAreaName,
        @Schema(description = "청약 시작일")
        String subscriptionStartDate,
        @Schema(description = "청약 종료일")
        String subscriptionEndDate,
        @Schema(description = "홈페이지 url")
        String webUrl,
        @Schema(description = "청약홈 url")
        String subscriptionHomeUrl,
        @Schema(description = "D-Day")
        int dDay
) {

    public static RentPblancSummaryResponse from(RentPblanc rentPblanc) {
        return new RentPblancSummaryResponse(
                rentPblanc.id(),
                rentPblanc.bsnsMbyNm(),
                rentPblanc.houseDetailSecdNm(),
                rentPblanc.houseNm(),
                rentPblanc.hssplyAdres(),
                rentPblanc.subscrptAreaCode(),
                rentPblanc.subscrptAreaCodeNm(),
                rentPblanc.subscrptRceptBgnde(),
                rentPblanc.subscrptRceptEndde(),
                rentPblanc.hmpgAdres(),
                rentPblanc.pblancUrl(),
                DDayUtils.calculateDDay(rentPblanc.subscrptRceptBgnde())
        );
    }

}