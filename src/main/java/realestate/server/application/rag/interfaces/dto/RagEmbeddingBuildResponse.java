package realestate.server.application.rag.interfaces.dto;

import realestate.server.application.rag.domain.RagEmbeddingBuildResult;

public record RagEmbeddingBuildResponse(
        int embeddedCount,
        int skippedCount,
        int failedCount
) {
    public static RagEmbeddingBuildResponse from(RagEmbeddingBuildResult result) {
        return new RagEmbeddingBuildResponse(
                result.embeddedCount(),
                result.skippedCount(),
                result.failedCount()
        );
    }
}
