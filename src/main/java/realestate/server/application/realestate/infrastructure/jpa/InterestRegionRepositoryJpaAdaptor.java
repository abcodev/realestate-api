package realestate.server.application.realestate.infrastructure.jpa;

import realestate.server.application.interest.infrastructure.jpa.entity.UserInterestJpaEntity;
import realestate.server.application.interest.infrastructure.jpa.repository.UserInterestJpaRepository;
import realestate.server.application.realestate.domain.InterestRegion;
import realestate.server.application.realestate.domain.InterestRegionRepository;
import realestate.server.application.realestate.domain.RegionCode;
import realestate.server.application.realestate.infrastructure.jpa.entity.InterestRegionJpaEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class InterestRegionRepositoryJpaAdaptor implements InterestRegionRepository {

    private final realestate.server.application.realestate.infrastructure.jpa.repository.InterestRegionRepository jpaRepository;
    private final UserInterestJpaRepository userInterestJpaRepository;

    private static final String CATEGORY = "real_estate";

    @Override
    public Optional<InterestRegion> findByUserId(Long userId) {
        return resolveInterestId(userId)
                .flatMap(jpaRepository::findByInterest_InterestId)
                .map(InterestRegionJpaEntity::toDomain);
    }

    /**
     * 관심 지역 저장/업데이트.
     * - user_interest 테이블에서 category=real_estate 인 interest_id를 조회
     * - 기존 데이터 있음 → JPA 관리 엔티티를 직접 수정 (더티체킹 UPDATE)
     * - 기존 데이터 없음 → 새 엔티티 생성 후 persist (INSERT)
     */
    @Override
    @Transactional
    public void saveOrUpdate(Long userId, RegionCode regionCode) {
        UserInterestJpaEntity interest = getOrCreateInterest(userId);

        Optional<InterestRegionJpaEntity> existing =
                jpaRepository.findByInterest_InterestId(interest.getInterestId());

        if (existing.isPresent()) {
            existing.get().changeRegion(regionCode);
        } else {
            InterestRegionJpaEntity newEntity = new InterestRegionJpaEntity(
                    interest,
                    regionCode.getFullRegionCode(),
                    regionCode.sggCode(),
                    regionCode.umdCode()
            );
            jpaRepository.save(newEntity);
        }
    }

    @Override
    @Transactional
    public void deleteByUserId(Long userId) {
        resolveInterestId(userId)
                .ifPresent(jpaRepository::deleteByInterest_InterestId);
    }

    /**
     * user_interest 에서 category=real_estate 인 interest_id 를 조회합니다.
     */
    private Optional<Long> resolveInterestId(Long userId) {
        return userInterestJpaRepository.findByUserIdAndCategory(userId, CATEGORY)
                .map(UserInterestJpaEntity::getInterestId);
    }

    /**
     * user_interest 에서 category=real_estate 인 레코드를 조회하거나 없으면 생성합니다.
     */
    private UserInterestJpaEntity getOrCreateInterest(Long userId) {
        return userInterestJpaRepository.findByUserIdAndCategory(userId, CATEGORY)
                .orElseGet(() -> userInterestJpaRepository.save(
                        UserInterestJpaEntity.builder()
                                .userId(userId)
                                .category(CATEGORY)
                                .isActive("Y")
                                .build()
                ));
    }
}
