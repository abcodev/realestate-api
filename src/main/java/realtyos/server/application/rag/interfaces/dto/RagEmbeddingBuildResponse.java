package realtyos.server.application.rag.interfaces.dto;

import realtyos.server.application.rag.domain.RagEmbeddingBuildResult;

public record RagEmbeddingBuildResponse(
        String provider,
        String model,
        int embeddedCount,
        int skippedCount,
        int failedCount
) {
    public static RagEmbeddingBuildResponse from(RagEmbeddingBuildResult result) {
        return new RagEmbeddingBuildResponse(
                result.provider(),
                result.model(),
                result.embeddedCount(),
                result.skippedCount(),
                result.failedCount()
        );
    }
}
