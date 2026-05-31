package realestate.server.application.rag.interfaces.dto;

import realestate.server.application.rag.domain.RagSearchResult;

public record RagSearchResponse(
        Long documentId,
        String embeddingProvider,
        String embeddingModel,
        String title,
        String content,
        String apartmentName,
        String region,
        String sourceType,
        Long sourceId,
        String dealDate,
        String exclusiveArea,
        Long dealAmount,
        String floor,
        String buildYear,
        double distance,
        double similarity,
        double recencyScore,
        double finalScore
) {
    public static RagSearchResponse from(RagSearchResult result) {
        return new RagSearchResponse(
                result.documentId(),
                result.embeddingProvider(),
                result.embeddingModel(),
                result.title(),
                result.content(),
                result.apartmentName(),
                result.region(),
                result.sourceType(),
                result.sourceId(),
                result.dealDate(),
                result.exclusiveArea(),
                result.dealAmount(),
                result.floor(),
                result.buildYear(),
                result.distance(),
                result.similarity(),
                result.recencyScore(),
                result.finalScore()
        );
    }
}
