package realestate.server.application.user.infrastructure.jpa.repository;

import realestate.server.application.auth.domain.Oauth2Provider;
import realestate.server.application.user.infrastructure.jpa.entity.UserJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UserJpaRepository extends JpaRepository<UserJpaEntity, Long> {

    Optional<UserJpaEntity> findById(Long userId);

    @Query("SELECT u FROM UserJpaEntity u WHERE u.oauth2Provider = :provider AND (u.providerId = :id OR u.email = :id OR (:email IS NOT NULL AND u.email = :email))")
    Optional<UserJpaEntity> findByProviderAndIdOrEmail(@Param("provider") Oauth2Provider provider, @Param("id") String id, @Param("email") String email);

    Optional<UserJpaEntity> findByOauth2ProviderAndEmail(Oauth2Provider oauth2Provider, String email);

    List<UserJpaEntity> findByPushEnabledAndBriefingPushTime(String pushEnabled, String briefingPushTime);
}
