package realestate.server.application.realestate.application.service;

import realestate.server.application.realestate.domain.*;
import realestate.server.application.realestate.interfaces.dto.AptPblancSummaryResponse;
import realestate.server.application.realestate.interfaces.dto.DealsSummaryResponse;
import realestate.server.application.realestate.interfaces.dto.RealEstateSummaryResponse;
import realestate.server.application.realestate.interfaces.dto.RentPblancSummaryResponse;
import realestate.server.application.realestate.interfaces.dto.RealEstateNewsSummaryDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class RealEstateSummaryService {

    private final DealsRepository dealsRepository;
    private final AptPblancRepository aptPblancRepository;
    private final RentPblancRepository rentPblancRepository;
    private final RealEstateInterestService realEstateInterestService;
    private final BgdCodeRepository bgdCodeRepository;

    private final RealEstateNewsSummaryRepository newsSummaryRepository;

    /**
     * 부동산 요약 정보 조회 (회원/비회원 통합)
     */
    public RealEstateSummaryResponse findSummary(RealEstateSummaryCommand command) {
        RegionCode regionCode = resolveRegionCode(command);
        
        String regionName = bgdCodeRepository.findByBgdCode(regionCode.getFullRegionCode())
                .map(BgdCode::bgdName)
                .orElse(null);

        List<DealsSummaryResponse> deals = findDealsByRegionCode(regionCode);
        List<AptPblancSummaryResponse> aptPblancs = findAptPblancsBySggCode(regionCode.sggCode());
        List<RentPblancSummaryResponse> rentPblancs = findRentPblancsBySggCode(regionCode.sggCode());

        RealEstateNewsSummaryDto newsSummaryDto = newsSummaryRepository.findLatest()
                .map(RealEstateNewsSummaryDto::from)
                .orElse(null);

        log.info("부동산 {} {} {}", deals, aptPblancs, rentPblancs);

        return new RealEstateSummaryResponse(regionName, deals, aptPblancs, rentPblancs, newsSummaryDto);
    }

    /**
     * Command 기반으로 RegionCode 생성
     */
    private RegionCode resolveRegionCode(RealEstateSummaryCommand command) {
        if (command.isMember()) {
            return realEstateInterestService.findUserInterestRegionCode(command.userId());
        }
        return RegionCode.fromBgdCode(command.bgdCode());
    }

    /**
     * sggCode, umdCode 해당하는 부동산 거래 정보 조회
     */
    private List<DealsSummaryResponse> findDealsByRegionCode(RegionCode regionCode) {
        return dealsRepository.findByInterestRegion(regionCode)
                .stream()
                .map(DealsSummaryResponse::from)
                .toList();
    }

    /**
     * sggCode 맞는 아파트 청약 정보 조회
     */
    private List<AptPblancSummaryResponse> findAptPblancsBySggCode(String sggCode) {
        String convertedSggCode = toSidoCode(sggCode);
        return aptPblancRepository.findBySggCode(convertedSggCode)
                .stream()
                .map(AptPblancSummaryResponse::from)
                .toList();
    }

    /**
     * sggCode 맞는 임대 정보 조회
     */
    private List<RentPblancSummaryResponse> findRentPblancsBySggCode(String sggCode) {
        String convertedSggCode = toSidoCode(sggCode);
        return rentPblancRepository.findBySggCode(convertedSggCode)
                .stream()
                .map(RentPblancSummaryResponse::from)
                .toList();
    }

    private String toSidoCode(String sggCode) {
        if (sggCode == null || sggCode.length() < 2) {
            throw new IllegalArgumentException("올바르지 않은 sggCode " + sggCode);
        }
        return sggCode.substring(0, 2) + "000";
    }

}