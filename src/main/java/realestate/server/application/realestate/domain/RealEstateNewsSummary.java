package realestate.server.application.realestate.domain;

import lombok.Builder;
import java.time.LocalDate;

@Builder
public record RealEstateNewsSummary(
        Long id,
        LocalDate summaryDate,
        String oneLineBrief,
        String summary
) {
}
