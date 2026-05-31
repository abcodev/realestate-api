package realtyos.server.application.user.domain;

import lombok.Builder;
import java.time.LocalDateTime;

@Builder
public record UserHistory(
        Long id,
        Long userId,
        realtyos.server.application.auth.domain.Oauth2Provider oauth2Provider,
        String providerId,
        LocalDateTime createdAt,
        LocalDateTime deletedAt
) {
    public static UserHistory createNew(Long userId, realtyos.server.application.auth.domain.Oauth2Provider oauth2Provider, String providerId, LocalDateTime joinedAt) {
        return UserHistory.builder()
                .userId(userId)
                .oauth2Provider(oauth2Provider)
                .providerId(providerId)
                .createdAt(joinedAt)
                .deletedAt(null)
                .build();
    }

    public UserHistory markDeleted() {
        return new UserHistory(
                this.id,
                this.userId,
                this.oauth2Provider,
                this.providerId,
                this.createdAt,
                LocalDateTime.now()
        );
    }
}
