package realestate.server.application.rag.client.openai;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import realestate.server.application.common.ai.config.AiConfig;
import realestate.server.application.rag.domain.EmbeddingClient;
import realestate.server.application.rag.domain.EmbeddingProvider;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class OpenAiEmbeddingClient implements EmbeddingClient {

    private static final String EMBEDDINGS_URL = "https://api.openai.com/v1/embeddings";
    private static final String DEFAULT_EMBEDDING_MODEL = "text-embedding-3-small";

    private final WebClient webClient;
    private final AiConfig aiConfig;
    private final ObjectMapper objectMapper;

    @Override
    public EmbeddingProvider provider() {
        return EmbeddingProvider.OPENAI;
    }

    @Override
    public String defaultModel() {
        return resolveEmbeddingModel();
    }

    @Override
    public List<List<Double>> embed(String model, List<String> inputs) {
        if (inputs.isEmpty()) {
            return List.of();
        }
        if (!StringUtils.hasText(aiConfig.getOpenai().getKey())) {
            throw new IllegalStateException("OpenAI API key가 설정되지 않았습니다. OPENAI_API_KEY 또는 ai.openai.key를 설정하세요.");
        }

        try {
            ObjectNode requestBody = objectMapper.createObjectNode();
            requestBody.put("model", model);
            requestBody.put("encoding_format", "float");
            ArrayNode inputArray = requestBody.putArray("input");
            inputs.forEach(inputArray::add);

            String responseBody = webClient.post()
                    .uri(EMBEDDINGS_URL)
                    .header(HttpHeaders.AUTHORIZATION, "Bearer " + aiConfig.getOpenai().getKey())
                    .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                    .bodyValue(requestBody.toString())
                    .retrieve()
                    .onStatus(HttpStatusCode::isError, response -> response.bodyToMono(String.class)
                            .defaultIfEmpty("")
                            .flatMap(body -> Mono.error(new IllegalStateException(
                                    "OpenAI embedding API error - status: %s, body: %s"
                                            .formatted(response.statusCode(), body)))))
                    .bodyToMono(String.class)
                    .block();

            return extractEmbeddings(responseBody);
        } catch (Exception e) {
            log.error("OpenAI embedding API 호출 실패", e);
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    private String resolveEmbeddingModel() {
        if (StringUtils.hasText(aiConfig.getOpenai().getEmbeddingModel())) {
            return aiConfig.getOpenai().getEmbeddingModel();
        }
        return DEFAULT_EMBEDDING_MODEL;
    }

    private List<List<Double>> extractEmbeddings(String responseBody) throws Exception {
        JsonNode data = objectMapper.readTree(responseBody).path("data");
        List<EmbeddingItem> items = new ArrayList<>();

        for (JsonNode item : data) {
            List<Double> embedding = new ArrayList<>();
            for (JsonNode value : item.path("embedding")) {
                embedding.add(value.asDouble());
            }
            items.add(new EmbeddingItem(item.path("index").asInt(), embedding));
        }

        return items.stream()
                .sorted(Comparator.comparingInt(EmbeddingItem::index))
                .map(EmbeddingItem::embedding)
                .toList();
    }

    private record EmbeddingItem(
            int index,
            List<Double> embedding
    ) {
    }
}
