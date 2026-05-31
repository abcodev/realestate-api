package realtyos.server.application.rag.interfaces.dto;

public record UserAiMemoryUpdateRequest(
        String preferredRegion,
        Long minPrice,
        Long maxPrice
) {
}
