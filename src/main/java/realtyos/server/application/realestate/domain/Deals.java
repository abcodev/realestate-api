package realtyos.server.application.realestate.domain;

import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record Deals(
        Long id,
        String sggCode,
        String umdCode,
        String landCode,
        String bonbun,
        String bubun,
        String roadName,
        String roadNameSggCode,
        String roadNameCode,
        String roadNameSeq,
        String roadNamebCode,
        String roadNameBonbun,
        String roadNameBubun,
//        String region1depthName,
        String umdName,
        String aptName,
        String jibun,
        String excluUseArea,
        Integer dealYear,
        Integer dealMonth,
        Integer dealDay,
        String dealAmount,
        String floor,
        String buildYear,
        String aptSeq,
        String cdealType,
        String cdealDay,
        String dealingType,
        String estateAgentSggName,
        String rgstDate,
        String aptDong,
        String slerType,
        String buyerType,
        String landLeaseholdType,
        LocalDateTime createdAt) {
}
