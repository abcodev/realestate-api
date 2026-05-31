package realestate.server.application.rag.domain;

public record RagEmbeddingBuildResult(
        int embeddedCount,
        int skippedCount,
        int failedCount
) {
}
