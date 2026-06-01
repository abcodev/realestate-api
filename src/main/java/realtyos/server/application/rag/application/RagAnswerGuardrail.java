package realtyos.server.application.rag.application;

import org.springframework.stereotype.Component;
import realtyos.server.application.rag.domain.RagSearchCondition;
import realtyos.server.application.rag.domain.RagSearchResult;

import java.util.List;
import java.util.Locale;

@Component
public class RagAnswerGuardrail {

    private static final String NO_MATCHING_EVIDENCE = "일치하는 근거를 찾지 못했습니다.";

    public boolean hasUsableEvidence(RagSearchCondition condition, List<RagSearchResult> results) {
        if (results == null || results.isEmpty()) {
            return false;
        }
        return results.stream().anyMatch(result -> matchesCondition(condition, result));
    }

    public String finalizeAnswer(String answer, List<RagSearchResult> results) {
        if (answer == null || answer.isBlank()) {
            return buildEvidenceSummary(results);
        }
        if (answer.contains("일치하는 근거를 찾지 못했습니다") && results != null && !results.isEmpty()) {
            return buildEvidenceSummary(results);
        }
        return answer;
    }

    public String noMatchingEvidenceMessage() {
        return NO_MATCHING_EVIDENCE;
    }

    private boolean matchesCondition(RagSearchCondition condition, RagSearchResult result) {
        if (condition == null) {
            return true;
        }
        if (condition.minPrice() != null && (result.dealAmount() == null || result.dealAmount() < condition.minPrice())) {
            return false;
        }
        if (condition.maxPrice() != null && (result.dealAmount() == null || result.dealAmount() > condition.maxPrice())) {
            return false;
        }
        Double area = parseArea(result.exclusiveArea());
        if (condition.minArea() != null && (area == null || area < condition.minArea())) {
            return false;
        }
        if (condition.maxArea() != null && (area == null || area > condition.maxArea())) {
            return false;
        }
        if (hasText(condition.apartmentName()) && !contains(result.apartmentName(), condition.apartmentName())) {
            return false;
        }
        if (hasText(condition.region()) && !matchesRegion(condition.region(), result)) {
            return false;
        }
        return true;
    }

    private boolean matchesRegion(String region, RagSearchResult result) {
        String normalizedRegion = region.trim();
        if (normalizedRegion.equals("강남") || normalizedRegion.equals("강남구")) {
            return contains(result.region(), "11680");
        }
        return contains(result.region(), normalizedRegion)
                || contains(result.apartmentName(), normalizedRegion);
    }

    private String buildEvidenceSummary(List<RagSearchResult> results) {
        if (results == null || results.isEmpty()) {
            return NO_MATCHING_EVIDENCE;
        }

        StringBuilder summary = new StringBuilder();
        summary.append("검색된 실거래 근거 기준으로 요약하면 다음과 같습니다.\n\n");
        results.stream().limit(5).forEach(result -> summary.append("- ")
                .append(safe(result.apartmentName())).append(" / ")
                .append(safe(result.region())).append(" / ")
                .append(safe(result.dealDate())).append(" / ")
                .append(result.dealAmount() == null ? "금액 정보없음" : result.dealAmount().toString() + "만원").append(" / ")
                .append(safe(result.exclusiveArea())).append("㎡")
                .append("\n"));
        summary.append("\n위 내용은 제공된 RAG 근거 문서에 있는 거래만 기준으로 작성했습니다.");
        return summary.toString();
    }

    private boolean contains(String source, String keyword) {
        if (!hasText(source) || !hasText(keyword)) {
            return false;
        }
        return source.toLowerCase(Locale.ROOT).contains(keyword.trim().toLowerCase(Locale.ROOT));
    }

    private boolean hasText(String value) {
        return value != null && !value.isBlank();
    }

    private Double parseArea(String value) {
        if (!hasText(value)) {
            return null;
        }
        try {
            return Double.parseDouble(value);
        } catch (NumberFormatException e) {
            return null;
        }
    }

    private String safe(String value) {
        return hasText(value) ? value : "정보없음";
    }
}
