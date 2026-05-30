package realestate.server.application.realestate.domain;

import lombok.Builder;

@Builder
public record SggCode(
        Long id,
        String sggCd,
        String fullNm,
        String sigKorNm,
        String sigEngNm) {
}
