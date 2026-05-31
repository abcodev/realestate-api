package realtyos.server.application.rag.application;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import realtyos.server.application.common.ai.AiProvider;
import realtyos.server.application.common.ai.AiService;
import realtyos.server.application.rag.domain.RagAnswer;
import realtyos.server.application.rag.domain.RagAnswerSource;
import realtyos.server.application.rag.domain.RagSearchCondition;
import realtyos.server.application.rag.domain.RagSearchResult;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class RagAnswerService {

    private static final String ENTITY_TYPE = "RAG_REALESTATE";

    private final RagSearchService searchService;
    private final AiService aiService;

    public RagAnswer answer(String query, Integer topK, String embeddingProvider, String embeddingModel,
                            String answerProvider, String answerModel, RagSearchCondition condition) {
        List<RagSearchResult> searchResults = searchService.search(
                query,
                topK,
                embeddingProvider,
                embeddingModel,
                condition
        );
        if (searchResults.isEmpty()) {
            return new RagAnswer("관련 실거래가 문서를 찾지 못했습니다.", List.of());
        }

        String prompt = buildPrompt(query, searchResults);
        String answer = aiService.ask(resolveAnswerProvider(answerProvider), ENTITY_TYPE, prompt, answerModel);
        List<RagAnswerSource> sources = searchResults.stream()
                .map(RagAnswerSource::from)
                .toList();

        log.info("RAG answer completed - query: {}, sourceCount: {}", query, sources.size());
        return new RagAnswer(answer, sources);
    }

    private AiProvider resolveAnswerProvider(String answerProvider) {
        if (answerProvider == null || answerProvider.isBlank()) {
            return AiProvider.OPENAI;
        }
        return AiProvider.valueOf(answerProvider.trim().toUpperCase());
    }

    private String buildPrompt(String query, List<RagSearchResult> searchResults) {
        String context = searchResults.stream()
                .map(this::formatDocument)
                .collect(Collectors.joining("\n\n"));

        return """
                아래 RAG 문서만 근거로 사용자 질문에 답변하세요.
                문서에 없는 사실은 추정하지 말고 알 수 없다고 답하세요.
                답변에는 가능한 경우 아래 표를 포함하세요.
                | 아파트명 | 거래일 | 전용면적 | 거래금액 | 층 | 근거문서ID |
                금액은 만원 단위 원문과 억원 환산 표현을 함께 사용하세요.
                검색 결과가 여러 건이면 최근 거래와 유사도가 높은 거래를 우선 요약하세요.

                [RAG 문서]
                %s

                [사용자 질문]
                %s
                """.formatted(context, query);
    }

    private String formatDocument(RagSearchResult result) {
        return """
                문서ID: %d
                제목: %s
                거래일: %s
                아파트명: %s
                전용면적: %s㎡
                거래금액: %s만원
                층: %s
                유사도: %.4f
                최종점수: %.4f
                내용:
                %s
                """.formatted(
                result.documentId(),
                result.title(),
                result.dealDate(),
                result.apartmentName(),
                result.exclusiveArea(),
                result.dealAmount(),
                result.floor(),
                result.similarity(),
                result.finalScore(),
                result.content()
        );
    }
}
