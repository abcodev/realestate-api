package realestate.server.application.realestate.domain;

import java.util.Optional;

/**
 * 부동산 관심 지역 저장소 포트.
 * 도메인 레이어에서 정의하고, 인프라 레이어에서 구현합니다.
 */
public interface InterestRegionRepository {

    Optional<InterestRegion> findByUserId(Long userId);

    void saveOrUpdate(Long userId, RegionCode regionCode);

    void deleteByUserId(Long userId);
}
