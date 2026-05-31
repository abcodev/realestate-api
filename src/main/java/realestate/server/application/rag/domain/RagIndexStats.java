package realestate.server.application.rag.domain;

import java.util.List;

public record RagIndexStats(
        long documentCount,
        long changedDocumentCount,
        long missingEmbeddingCount,
        List<EmbeddingStats> embeddings
) {
    public record EmbeddingStats(
            String provider,
            String model,
            Integer dimension,
            long count
    ) {
    }
}
