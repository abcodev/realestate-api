package realestate.server.application.realestate.application.service;

import realestate.server.application.common.ai.AiProvider;
import realestate.server.application.common.ai.AiService;
import realestate.server.application.realestate.domain.RealEstateNewsSummary;
import realestate.server.application.realestate.domain.RealEstateNewsSummaryRepository;
import realestate.server.application.realestate.infrastructure.client.HankyungRssClient;
import realestate.server.application.realestate.infrastructure.client.dto.RssFeedResponse;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class RealEstateNewsService {

    private static final String ENTITY_TYPE = "REAL_ESTATE_NEWS";

    private final HankyungRssClient hankyungRssClient;
    private final AiService aiService;
    private final RealEstateNewsSummaryRepository newsSummaryRepository;
    private final ObjectMapper objectMapper;

    @Transactional
    public void processDailyRealEstateNews() {
        log.info("=== 부동산 RSS 뉴스 AI 요약 배치 시작 ===");
        try {
            LocalDate today = LocalDate.now();
            ZonedDateTime now = ZonedDateTime.now();

            RssFeedResponse response = hankyungRssClient.fetchRealEstateNews();

            if (response == null || response.getChannel() == null || response.getChannel().getItems() == null) {
                log.warn("RSS 데이터를 가져오지 못했습니다.");
                return;
            }

            List<RssFeedResponse.Item> items = response.getChannel().getItems();
            
            // 최근 24시간 필터링
            List<String> validTitles = items.stream()
                    .filter(item -> RssDateParser.isWithin24Hours(item, now))
                    .map(RssFeedResponse.Item::getTitle)
                    .map(title -> "- " + title.replace("<![CDATA[", "").replace("]]>", "").trim())
                    .toList();

            log.info("부동산 RSS 24h 필터링 완료: 전체 {}건 중 {}건 추려냄", items.size(), validTitles.size());

            if (validTitles.isEmpty()) {
                log.info("최근 24시간 내에 작성된 부동산 뉴스가 없습니다.");
                return;
            }

            String allTitles = String.join("\n", validTitles);
            
            // 기존 오늘의 데이터가 있다면 삭제
            newsSummaryRepository.deleteBySummaryDate(today);

            AiAnalysisResult result = analyzeWithAi(allTitles);

            RealEstateNewsSummary summary = RealEstateNewsSummary.builder()
                    .summaryDate(today)
                    .oneLineBrief(result.oneLineBrief())
                    .summary(result.summary())
                    .build();

            newsSummaryRepository.save(summary);
            log.info("=== 부동산 RSS 뉴스 AI 요약 배치 완료 ===");
        } catch (Exception e) {
            log.error("부동산 RSS 뉴스 AI 요약 실패", e);
            throw e;
        }
    }

    private AiAnalysisResult analyzeWithAi(String titlesText) {
        log.info("[부동산 뉴스] AI 분석 요청 (텍스트 길이: {})", titlesText.length());

        try {
            String aiResponse = aiService.ask(AiProvider.GEMINI, ENTITY_TYPE, titlesText);
            log.info("[부동산 뉴스] AI 분석 완료 (응답 길이: {})", aiResponse.length());
            return parseAiResponse(aiResponse);
        } catch (Exception e) {
            log.error("[부동산 뉴스] AI 분석 실패, 기본 텍스트 반환", e);
            return new AiAnalysisResult("부동산 시세 요약", "현재 부동산 주요 지표를 요약할 수 없습니다.");
        }
    }

    private AiAnalysisResult parseAiResponse(String aiResponse) {
        try {
            String cleaned = aiResponse.trim();
            if (cleaned.startsWith("```")) {
                int firstNewline = cleaned.indexOf('\n');
                if (firstNewline > 0) cleaned = cleaned.substring(firstNewline + 1);
                if (cleaned.endsWith("```")) cleaned = cleaned.substring(0, cleaned.lastIndexOf("```"));
                cleaned = cleaned.trim();
            }

            JsonNode json = objectMapper.readTree(cleaned);
            String oneLineBrief = json.path("oneLineBrief").asText("부동산 시세 브리핑");
            String summary = json.path("summary").asText("브리핑 내용을 불러올 수 없습니다.");

            return new AiAnalysisResult(oneLineBrief, summary);
        } catch (Exception e) {
            log.debug("[부동산 뉴스] JSON 직접 파싱 실패, regex 추출 시도");
            String oneLineBrief = extractJsonField(aiResponse, "oneLineBrief");
            String summary = extractJsonField(aiResponse, "summary");
            if (oneLineBrief != null && summary != null) {
                return new AiAnalysisResult(oneLineBrief, summary);
            }
            return new AiAnalysisResult("어제 부동산 뉴스 브리핑", aiResponse.replaceAll("```[a-zA-Z]*", "").replace("```", "").trim());
        }
    }

    private String extractJsonField(String text, String fieldName) {
        java.util.regex.Pattern pattern = java.util.regex.Pattern.compile(
                "\"" + fieldName + "\"\\s*:\\s*\"((?:[^\"\\\\]|\\\\.)*)\"?",
                java.util.regex.Pattern.DOTALL);
        java.util.regex.Matcher matcher = pattern.matcher(text);
        if (matcher.find()) {
            return matcher.group(1).replace("\\\"", "\"").replace("\\n", "\n").trim();
        }
        return null;
    }

    private record AiAnalysisResult(String oneLineBrief, String summary) {}
}
