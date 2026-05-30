package realestate.server.application.realestate.infrastructure.jpa.repository;

import realestate.server.application.realestate.infrastructure.jpa.entity.InterestRegionJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface InterestRegionRepository extends JpaRepository<InterestRegionJpaEntity, Long> {

    Optional<InterestRegionJpaEntity> findByInterest_InterestId(Long interestId);

    void deleteByInterest_InterestId(Long interestId);
}
