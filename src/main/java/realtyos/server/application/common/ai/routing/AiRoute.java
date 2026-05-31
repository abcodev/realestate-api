package realtyos.server.application.common.ai.routing;

import realtyos.server.application.common.ai.AiProvider;

public record AiRoute(
        AiProvider provider,
        String model,
        AiProvider fallbackProvider,
        String fallbackModel,
        String reason
) {
}
