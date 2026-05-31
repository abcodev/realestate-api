package realtyos.server.application.rag.interfaces.dto;

import realtyos.server.application.rag.domain.RagSearchCondition;

public final class RagSearchConditionMapper {

    private RagSearchConditionMapper() {
    }

    public static RagSearchCondition from(RagSearchRequest request) {
        return new RagSearchCondition(
                request.region(),
                request.apartmentName(),
                request.fromYear(),
                request.fromMonth(),
                request.toYear(),
                request.toMonth(),
                request.minPrice(),
                request.maxPrice(),
                request.minArea(),
                request.maxArea(),
                request.recentFirst()
        );
    }

    public static RagSearchCondition from(RagAskRequest request) {
        return new RagSearchCondition(
                request.region(),
                request.apartmentName(),
                request.fromYear(),
                request.fromMonth(),
                request.toYear(),
                request.toMonth(),
                request.minPrice(),
                request.maxPrice(),
                request.minArea(),
                request.maxArea(),
                request.recentFirst()
        );
    }
}
