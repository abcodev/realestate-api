package realestate.server.application.common.ai.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * AI API 키 설정.
 * application.yml의 ai.openai.key, ai.gemini.key 값을 바인딩합니다.
 */
@Configuration
@ConfigurationProperties(prefix = "ai")
@Getter
@Setter
public class AiConfig {

    private OpenAi openai = new OpenAi();
    private Gemini gemini = new Gemini();

    @Getter
    @Setter
    public static class OpenAi {
        private String key;
        private String embeddingModel = "text-embedding-3-small";
    }

    @Getter
    @Setter
    public static class Gemini {
        private String key;
    }
}
