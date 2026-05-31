package realtyos.server.application.rag.interfaces.dto;

import realtyos.server.application.rag.memory.UserAiMemoryEventJpaEntity;

import java.time.LocalDateTime;

public record UserAiMemoryEventResponse(
        Long id,
        String query,
        String region,
        String apartmentName,
        Long minPrice,
        Long maxPrice,
        LocalDateTime createdAt
) {

    public static UserAiMemoryEventResponse from(UserAiMemoryEventJpaEntity event) {
        return new UserAiMemoryEventResponse(
                event.getId(),
                event.getQuery(),
                event.getRegion(),
                event.getApartmentName(),
                event.getMinPrice(),
                event.getMaxPrice(),
                event.getCreatedAt()
        );
    }
}
