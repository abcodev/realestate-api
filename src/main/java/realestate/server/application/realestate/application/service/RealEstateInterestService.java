package realestate.server.application.realestate.application.service;

import realestate.server.application.realestate.domain.BgdCodeRepository;
import realestate.server.application.realestate.domain.InterestRegionRepository;
import realestate.server.application.realestate.domain.RegionCode;
import realestate.server.application.realestate.interfaces.dto.RealEstateRegionResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class RealEstateInterestService {

    private final InterestRegionRepository interestRegionRepository;
    private final BgdCodeRepository bgdCodeRepository;

    @Transactional(readOnly = true)
    public RegionCode findUserInterestRegionCode(Long userId) {
        return interestRegionRepository.findByUserId(userId)
                .orElseThrow(() -> new IllegalArgumentException("관심 지역이 등록되지 않은 회원입니다. userId: " + userId))
                .toRegionCode();
    }

    @Transactional(readOnly = true)
    public List<RealEstateRegionResponse> searchRegionsByName(String regionName) {
        return bgdCodeRepository.findByBgdNameContainingAndNot1Depth(regionName)
                .stream()
                .map(bgdCode -> new RealEstateRegionResponse(bgdCode.bgdCode(), bgdCode.bgdName()))
                .toList();
    }

    @Transactional
    public void saveInterest(Long userId, RegionCode regionCode) {
        interestRegionRepository.saveOrUpdate(userId, regionCode);
    }
}
