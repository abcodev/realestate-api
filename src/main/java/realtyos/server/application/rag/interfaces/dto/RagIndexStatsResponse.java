package realtyos.server.application.rag.interfaces.dto;

import realtyos.server.application.rag.domain.RagIndexStats;

import java.util.List;

public record RagIndexStatsResponse(
        long documentCount,
        long changedDocumentCount,
        long missingEmbeddingCount,
        List<EmbeddingStatsResponse> embeddings
) {
    public static RagIndexStatsResponse from(RagIndexStats stats) {
        return new RagIndexStatsResponse(
                stats.documentCount(),
                stats.changedDocumentCount(),
                stats.missingEmbeddingCount(),
                stats.embeddings().stream()
                        .map(EmbeddingStatsResponse::from)
                        .toList()
        );
    }

    public record EmbeddingStatsResponse(
            String provider,
            String model,
            Integer dimension,
            long count
    ) {
        public static EmbeddingStatsResponse from(RagIndexStats.EmbeddingStats stats) {
            return new EmbeddingStatsResponse(
                    stats.provider(),
                    stats.model(),
                    stats.dimension(),
                    stats.count()
            );
        }
    }
}
