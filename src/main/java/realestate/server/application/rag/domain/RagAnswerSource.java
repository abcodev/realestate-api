package realestate.server.application.rag.domain;

public record RagAnswerSource(
        Long documentId,
        String title,
        String apartmentName,
        String region,
        String sourceType,
        Long sourceId,
        double distance,
        double similarity
) {
    public static RagAnswerSource from(RagSearchResult result) {
        return new RagAnswerSource(
                result.documentId(),
                result.title(),
                result.apartmentName(),
                result.region(),
                result.sourceType(),
                result.sourceId(),
                result.distance(),
                result.similarity()
        );
    }
}
