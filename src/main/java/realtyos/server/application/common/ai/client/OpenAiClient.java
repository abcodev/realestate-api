package realtyos.server.application.common.ai.client;

import realtyos.server.application.common.ai.AiClient;
import realtyos.server.application.common.ai.AiProvider;
import realtyos.server.application.common.ai.config.AiConfig;
import realtyos.server.application.common.ai.prompt.AiPromptTemplateJpaEntity;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

/**
 * OpenAI Chat Completions API 클라이언트.
 * <p>
 * DB 프롬프트 템플릿의 model, temperature, maxTokens 값을 사용합니다.
 * 값이 없으면 기본값 (gpt-4o-mini, 0.7, 1024)을 사용합니다.
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class OpenAiClient implements AiClient {

    private static final String BASE_URL = "https://api.openai.com/v1/chat/completions";
    private static final String DEFAULT_MODEL = "gpt-4o-mini";

    private final WebClient webClient;
    private final AiConfig aiConfig;
    private final ObjectMapper objectMapper;

    @Override
    public String chat(AiPromptTemplateJpaEntity template, String userMessage) {
        return chat(template, userMessage, null);
    }

    @Override
    public String chat(AiPromptTemplateJpaEntity template, String userMessage, String model) {
        try {
            String actualUserMessage = applyUserPromptTemplate(template, userMessage);
            ObjectNode requestBody = buildRequestBody(template, actualUserMessage, model);

            String responseBody = webClient.post()
                    .uri(BASE_URL)
                    .header(HttpHeaders.AUTHORIZATION, "Bearer " + aiConfig.getOpenai().getKey())
                    .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                    .bodyValue(requestBody.toString())
                    .retrieve()
                    .bodyToMono(String.class)
                    .block();

            return extractContent(responseBody);
        } catch (Exception e) {
            log.error("OpenAI API 호출 실패", e);
            throw new RuntimeException("OpenAI API 호출 중 오류가 발생했습니다.", e);
        }
    }

    @Override
    public AiProvider getProvider() {
        return AiProvider.OPENAI;
    }

    private String applyUserPromptTemplate(AiPromptTemplateJpaEntity template, String userMessage) {
        if (template.getUserPromptTemplate() != null && !template.getUserPromptTemplate().isBlank()) {
            return template.getUserPromptTemplate().replace("{{content}}", userMessage);
        }
        return userMessage;
    }

    private ObjectNode buildRequestBody(AiPromptTemplateJpaEntity template, String userMessage, String modelOverride) {
        ObjectNode body = objectMapper.createObjectNode();

        String model = modelOverride != null && !modelOverride.isBlank()
                ? modelOverride
                : template.getModel() != null ? template.getModel() : DEFAULT_MODEL;
        body.put("model", model);

        if (template.getTemperature() != null) {
            body.put("temperature", template.getTemperature().doubleValue());
        }
        if (template.getMaxTokens() != null) {
            body.put("max_tokens", template.getMaxTokens());
        }

        ArrayNode messages = body.putArray("messages");

        ObjectNode systemMsg = messages.addObject();
        systemMsg.put("role", "system");
        systemMsg.put("content", template.getSystemPrompt());

        ObjectNode userMsg = messages.addObject();
        userMsg.put("role", "user");
        userMsg.put("content", userMessage);

        return body;
    }

    private String extractContent(String responseBody) {
        try {
            JsonNode root = objectMapper.readTree(responseBody);
            return root.path("choices").get(0).path("message").path("content").asText();
        } catch (Exception e) {
            log.error("OpenAI 응답 파싱 실패: {}", responseBody, e);
            throw new RuntimeException("OpenAI 응답 파싱 중 오류가 발생했습니다.", e);
        }
    }
}
