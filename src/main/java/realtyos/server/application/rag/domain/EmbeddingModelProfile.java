package realtyos.server.application.rag.domain;

public record EmbeddingModelProfile(
        EmbeddingProvider provider,
        String model
) {
}
