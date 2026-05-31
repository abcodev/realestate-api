package realtyos.server.application.common.ai.routing;

import org.junit.jupiter.api.Test;
import realtyos.server.application.common.ai.AiProvider;
import realtyos.server.application.common.ai.config.AiConfig;

import static org.assertj.core.api.Assertions.assertThat;

class AiModelRouterTest {

    private final AiConfig config = new AiConfig();
    private final AiModelRouter router = new AiModelRouter(config);

    @Test
    void routesShortRagRequestToLocalModelWithHighQualityFallback() {
        AiRoute route = router.route("RAG_REALESTATE", "강남 최근 거래 알려줘", null, null);

        assertThat(route.provider()).isEqualTo(AiProvider.OLLAMA);
        assertThat(route.model()).isEqualTo("qwen3:8b");
        assertThat(route.fallbackProvider()).isEqualTo(AiProvider.OPENAI);
        assertThat(route.reason()).isEqualTo("rag-local-first");
    }

    @Test
    void routesLongRagRequestToHighQualityModel() {
        String longQuestion = "강남 실거래가를 투자 관점에서 분석해줘 ".repeat(400);

        AiRoute route = router.route("RAG_REALESTATE", longQuestion, null, null);

        assertThat(route.provider()).isEqualTo(AiProvider.OPENAI);
        assertThat(route.model()).isEqualTo("gpt-4o-mini");
        assertThat(route.reason()).isEqualTo("rag-high-quality");
    }

    @Test
    void explicitProviderBypassesAutomaticRouting() {
        AiRoute route = router.route("RAG_REALESTATE", "강남", AiProvider.OPENAI, "gpt-4.1-mini");

        assertThat(route.provider()).isEqualTo(AiProvider.OPENAI);
        assertThat(route.model()).isEqualTo("gpt-4.1-mini");
        assertThat(route.fallbackProvider()).isNull();
        assertThat(route.reason()).isEqualTo("explicit-provider");
    }
}
