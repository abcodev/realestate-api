package realestate.server.application.realestate.domain;

import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record RentPblanc(
        Long id,
        String houseManageNo,
        String pblancNo,
        String houseNm,
        String houseSecd,
        String houseSecdNm,
        String houseDetailSecd,
        String houseDetailSecdNm,
        String searchHouseSecd,
        String subscrptAreaCode,
        String subscrptAreaCodeNm,
        String sggCode,
        String rcritPblancDe,
        String nsprcNm,
        String subscrptRceptBgnde,
        String subscrptRceptEndde,
        String przwnerPresnatnDe,
        String hssplyZip,
        String hssplyAdres,
        Integer totSuplyHshldco,
        String cntrctCnclsBgnde,
        String cntrctCnclsEndde,
        String hmpgAdres,
        String bsnsMbyNm,
        String mdhsTelno,
        String mvnPrearngeYm,
        String pblancUrl,
        LocalDateTime createdAt) {
}
