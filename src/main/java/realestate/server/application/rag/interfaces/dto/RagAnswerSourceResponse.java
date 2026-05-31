package realestate.server.application.rag.interfaces.dto;

import realestate.server.application.rag.domain.RagAnswerSource;

public record RagAnswerSourceResponse(
        Long documentId,
        String title,
        String apartmentName,
        String region,
        String sourceType,
        Long sourceId,
        double distance,
        double similarity
) {
    public static RagAnswerSourceResponse from(RagAnswerSource source) {
        return new RagAnswerSourceResponse(
                source.documentId(),
                source.title(),
                source.apartmentName(),
                source.region(),
                source.sourceType(),
                source.sourceId(),
                source.distance(),
                source.similarity()
        );
    }
}
