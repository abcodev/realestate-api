package realtyos.server.application.common.ai.routing;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import realtyos.server.application.common.ai.AiProvider;
import realtyos.server.application.common.ai.config.AiConfig;

@Component
@RequiredArgsConstructor
public class AiModelRouter {

    private static final String RAG_REALESTATE = "RAG_REALESTATE";

    private final AiConfig aiConfig;

    public AiRoute route(String entityType, String userMessage, AiProvider requestedProvider, String requestedModel) {
        AiConfig.Router router = aiConfig.getRouter();
        if (!router.isEnabled()) {
            return requestedOrDefault(requestedProvider, requestedModel, router, "router-disabled");
        }
        if (requestedProvider != null) {
            return new AiRoute(requestedProvider, requestedModel, null, null, "explicit-provider");
        }
        if (RAG_REALESTATE.equals(entityType)) {
            return routeRealestateRag(userMessage, requestedModel, router);
        }
        return requestedOrDefault(null, requestedModel, router, "default-route");
    }

    private AiRoute routeRealestateRag(String userMessage, String requestedModel, AiConfig.Router router) {
        if (router.isLocalFirst() && messageLength(userMessage) <= router.getLocalMaxInputChars()) {
            return new AiRoute(
                    router.getLocalProvider(),
                    firstText(requestedModel, router.getLocalModel()),
                    router.getHighQualityProvider(),
                    router.getHighQualityModel(),
                    "rag-local-first"
            );
        }
        return new AiRoute(
                router.getHighQualityProvider(),
                firstText(requestedModel, router.getHighQualityModel()),
                router.getFallbackProvider(),
                router.getFallbackModel(),
                "rag-high-quality"
        );
    }

    private AiRoute requestedOrDefault(AiProvider requestedProvider, String requestedModel, AiConfig.Router router,
                                       String reason) {
        return new AiRoute(
                requestedProvider != null ? requestedProvider : router.getDefaultProvider(),
                firstText(requestedModel, router.getDefaultModel()),
                router.getFallbackProvider(),
                router.getFallbackModel(),
                reason
        );
    }

    private int messageLength(String userMessage) {
        return userMessage == null ? 0 : userMessage.length();
    }

    private String firstText(String first, String second) {
        return StringUtils.hasText(first) ? first : second;
    }
}
