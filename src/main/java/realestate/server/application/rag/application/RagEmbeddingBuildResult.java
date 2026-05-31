package realestate.server.application.rag.application;

public record RagEmbeddingBuildResult(
        int embeddedCount,
        int skippedCount,
        int failedCount
) {
}
