package realtyos.server.application.rag.domain;

public record RagSearchCondition(
        String region,
        String apartmentName,
        Integer fromYear,
        Integer fromMonth,
        Integer toYear,
        Integer toMonth,
        Long minPrice,
        Long maxPrice,
        Double minArea,
        Double maxArea,
        Boolean recentFirst
) {
}
