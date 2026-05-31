package realtyos.server.application.realestate.infrastructure.client;

import realtyos.server.application.realestate.domain.AptPblanc;
import realtyos.server.application.realestate.domain.BgdCodeRepository;
import realtyos.server.application.realestate.domain.Deals;
import realtyos.server.application.realestate.domain.RentPblanc;
import realtyos.server.application.realestate.domain.DataFetchPort;
import realtyos.server.application.realestate.infrastructure.client.dto.AptPblancApiResponse;
import realtyos.server.application.realestate.infrastructure.client.dto.RentPblancApiResponse;
import realtyos.server.application.realestate.infrastructure.client.dto.DealsApiResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@Component
@RequiredArgsConstructor
public class RealestateDataFetchAdaptor implements DataFetchPort {

    private final RealestateExternalApiClient apiClient;
    private final BgdCodeRepository bgdCodeRepository;

    private final Map<String, String> regionCodeCache = new ConcurrentHashMap<>();

    @Override
    public List<Deals> fetchDeals(String lawdCd, String dealYmd) {
        List<Deals> allDeals = new java.util.ArrayList<>();
        int pageNo = 1;
        int numOfRows = 100;
        int totalCount = 0;

        while (true) {
            realtyos.server.application.realestate.infrastructure.client.dto.DealsApiResponse response = apiClient
                    .fetchDeals(lawdCd, dealYmd, pageNo, numOfRows);

            if (response == null || response.getBody() == null || response.getBody().getItems() == null) {
                log.warn("No data found or invalid response for fetchDeals - lawdCd: {}, dealYmd: {}, pageNo: {}",
                        lawdCd, dealYmd, pageNo);
                break;
            }

            List<Deals> deals = response.getBody().getItems().stream()
                    .map(this::mapToDealsDomain)
                    .toList();
            allDeals.addAll(deals);

            totalCount = response.getBody().getTotalCount();
            log.info("Fetched Deals page {}/{} (total items: {})",
                    pageNo, (int) Math.ceil((double) totalCount / numOfRows), totalCount);

            if (pageNo * numOfRows >= totalCount) {
                break;
            }

            pageNo++;
        }

        return allDeals;
    }

    private Deals mapToDealsDomain(DealsApiResponse.Item item) {
        return Deals.builder()
                .sggCode(item.getSggCd())
                .umdCode(item.getUmdCd())
                .landCode(item.getLandCd())
                .bonbun(item.getBonbun())
                .bubun(item.getBubun())
                .roadName(item.getRoadNm())
                .roadNameSggCode(item.getRoadNmSggCd())
                .roadNameCode(item.getRoadNmCd())
                .roadNameSeq(item.getRoadNmSeq())
                .roadNamebCode(item.getRoadNmbCd())
                .roadNameBonbun(item.getRoadNmBonbun())
                .roadNameBubun(item.getRoadNmBubun())
                .umdName(item.getUmdNm())
                .aptName(item.getAptNm())
                .jibun(item.getJibun())
                .excluUseArea(item.getExcluUseAr())
                .dealYear(item.getDealYear())
                .dealMonth(item.getDealMonth())
                .dealDay(item.getDealDay())
                .dealAmount(item.getDealAmount())
                .floor(item.getFloor())
                .buildYear(item.getBuildYear())
                .aptSeq(item.getAptSeq())
                .cdealType(item.getCdealType())
                .cdealDay(item.getCdealDay())
                .dealingType(item.getDealingGbn())
                .estateAgentSggName(item.getEstateAgentSggNm())
                .rgstDate(item.getRgstDate())
                .aptDong(item.getAptDong())
                .slerType(item.getSlerGbn())
                .buyerType(item.getBuyerGbn())
                .landLeaseholdType(item.getLandLeaseholdGbn())
                .createdAt(LocalDateTime.now())
                .build();
    }

    @Override
    public List<AptPblanc> fetchAptLttotPblancDetail(int page, int perPage) {
        AptPblancApiResponse response = apiClient.fetchAptLttotPblancDetail(page, perPage);

        if (response == null || response.getData() == null || response.getData().isEmpty()) {
            log.warn("No data found or invalid response for APT Lttot Pblanc Detail page: {}, perPage: {}", page,
                    perPage);
            return Collections.emptyList();
        }

        return response.getData().stream()
                .map(this::mapToAptLttotPblancDetailDomain)
                .toList();
    }

    private AptPblanc mapToAptLttotPblancDetailDomain(AptPblancApiResponse.Data data) {
        String regionName = data.getSubscrptAreaCodeNm();
        String sggCode = null;
        if (regionName != null && !regionName.isBlank()) {
            String fullRegionName = mapToFullRegionName(regionName);
            sggCode = regionCodeCache.computeIfAbsent(fullRegionName,
                k -> bgdCodeRepository.findUmdCodePrefixByRegionName(k).orElse(null)
            );
        }

        return AptPblanc.builder()
                .houseManageNo(data.getHouseManageNo())
                .pblancNo(data.getPblancNo())
                .houseNm(data.getHouseNm())
                .houseSecd(data.getHouseSecd())
                .houseSecdNm(data.getHouseSecdNm())
                .houseDtlSecd(data.getHouseDtlSecd())
                .houseDtlSecdNm(data.getHouseDtlSecdNm())
                .rentSecd(data.getRentSecd())
                .rentSecdNm(data.getRentSecdNm())
                .subscrptAreaCode(data.getSubscrptAreaCode())
                .subscrptAreaCodeNm(data.getSubscrptAreaCodeNm())
                .sggCode(sggCode)
                .hssplyZip(data.getHssplyZip())
                .hssplyAdres(data.getHssplyAdres())
                .totSuplyHshldco(data.getTotSuplyHshldco())
                .rcritPblancDe(data.getRcritPblancDe())
                .nsprcNm(data.getNsprcNm())
                .rceptBgnde(data.getRceptBgnde())
                .rceptEndde(data.getRceptEndde())
                .spsplyRceptBgnde(data.getSpsplyRceptBgnde())
                .spsplyRceptEndde(data.getSpsplyRceptEndde())
                .gnrlRnk1CrspareaRcptde(data.getGnrlRnk1CrspareaRcptde())
                .gnrlRnk1CrspareaEndde(data.getGnrlRnk1CrspareaEndde())
                .gnrlRnk1EtcGgRcptde(data.getGnrlRnk1EtcGgRcptde())
                .gnrlRnk1EtcGgEndde(data.getGnrlRnk1EtcGgEndde())
                .gnrlRnk1EtcAreaRcptde(data.getGnrlRnk1EtcAreaRcptde())
                .gnrlRnk1EtcAreaEndde(data.getGnrlRnk1EtcAreaEndde())
                .gnrlRnk2CrspareaRcptde(data.getGnrlRnk2CrspareaRcptde())
                .gnrlRnk2CrspareaEndde(data.getGnrlRnk2CrspareaEndde())
                .gnrlRnk2EtcGgRcptde(data.getGnrlRnk2EtcGgRcptde())
                .gnrlRnk2EtcGgEndde(data.getGnrlRnk2EtcGgEndde())
                .gnrlRnk2EtcAreaRcptde(data.getGnrlRnk2EtcAreaRcptde())
                .gnrlRnk2EtcAreaEndde(data.getGnrlRnk2EtcAreaEndde())
                .przwnerPresnatnDe(data.getPrzwnerPresnatnDe())
                .cntrctCnclsBgnde(data.getCntrctCnclsBgnde())
                .cntrctCnclsEndde(data.getCntrctCnclsEndde())
                .hmpgAdres(data.getHmpgAdres())
                .cnstrctEntrpsNm(data.getCnstrctEntrpsNm())
                .mdhsTelno(data.getMdhsTelno())
                .bsnsMbyNm(data.getBsnsMbyNm())
                .mvnPrearngeYm(data.getMvnPrearngeYm())
                .specltRdnEarthAt(data.getSpecltRdnEarthAt())
                .mdatTrgetAreaSecd(data.getMdatTrgetAreaSecd())
                .parcprcUlsAt(data.getParcprcUlsAt())
                .imprmnBsnsAt(data.getImprmnBsnsAt())
                .publicHouseEarthAt(data.getPublicHouseEarthAt())
                .lrsclBldlndAt(data.getLrsclBldlndAt())
                .nplnPrvoprPublicHouseAt(data.getNplnPrvoprPublicHouseAt())
                .publicHouseSpclwApplcAt(data.getPublicHouseSpclwApplcAt())
                .pblancUrl(data.getPblancUrl())
                .createdAt(LocalDateTime.now())
                .build();
    }

    private String mapToFullRegionName(String shortName) {
        if (shortName == null) return null;
        return switch (shortName.trim()) {
            case "서울" -> "서울특별시";
            case "부산" -> "부산광역시";
            case "대구" -> "대구광역시";
            case "인천" -> "인천광역시";
            case "광주" -> "광주광역시";
            case "대전" -> "대전광역시";
            case "울산" -> "울산광역시";
            case "세종" -> "세종특별자치시";
            case "경기" -> "경기도";
            case "강원" -> "강원특별자치도";
            case "충북" -> "충청북도";
            case "충남" -> "충청남도";
            case "전북" -> "전북특별자치도";
            case "전남" -> "전라남도";
            case "경북" -> "경상북도";
            case "경남" -> "경상남도";
            case "제주" -> "제주특별자치도";
            default -> shortName;
        };
    }

    @Override
    public List<RentPblanc> fetchPblPvtRentLttotPblancDetail(int page, int perPage) {
        RentPblancApiResponse response = apiClient.fetchPblPvtRentLttotPblancDetail(page, perPage);

        if (response == null || response.getData() == null || response.getData().isEmpty()) {
            log.warn("No data found or invalid response for Pbl Pvt Rent Lttot Pblanc Detail page: {}, perPage: {}",
                    page, perPage);
            return Collections.emptyList();
        }

        return response.getData().stream()
                .map(this::mapToPblPvtRentLttotPblancDetailDomain)
                .toList();
    }

    private RentPblanc mapToPblPvtRentLttotPblancDetailDomain(
            RentPblancApiResponse.Data data) {
        String regionName = data.getSubscrptAreaCodeNm();
        String sggCode = null;
        if (regionName != null && !regionName.isBlank()) {
            String fullRegionName = mapToFullRegionName(regionName);
            sggCode = regionCodeCache.computeIfAbsent(fullRegionName,
                k -> bgdCodeRepository.findUmdCodePrefixByRegionName(k).orElse(null)
            );
        }

        return RentPblanc.builder()
                .houseManageNo(data.getHouseManageNo())
                .pblancNo(data.getPblancNo())
                .houseNm(data.getHouseNm())
                .houseSecd(data.getHouseSecd())
                .houseSecdNm(data.getHouseSecdNm())
                .houseDetailSecd(data.getHouseDetailSecd())
                .houseDetailSecdNm(data.getHouseDetailSecdNm())
                .searchHouseSecd(data.getSearchHouseSecd())
                .subscrptAreaCode(data.getSubscrptAreaCode())
                .subscrptAreaCodeNm(data.getSubscrptAreaCodeNm())
                .sggCode(sggCode)
                .rcritPblancDe(data.getRcritPblancDe())
                .nsprcNm(data.getNsprcNm())
                .subscrptRceptBgnde(data.getSubscrptRceptBgnde())
                .subscrptRceptEndde(data.getSubscrptRceptEndde())
                .przwnerPresnatnDe(data.getPrzwnerPresnatnDe())
                .hssplyZip(data.getHssplyZip())
                .hssplyAdres(data.getHssplyAdres())
                .totSuplyHshldco(data.getTotSuplyHshldco())
                .cntrctCnclsBgnde(data.getCntrctCnclsBgnde())
                .cntrctCnclsEndde(data.getCntrctCnclsEndde())
                .hmpgAdres(data.getHmpgAdres())
                .bsnsMbyNm(data.getBsnsMbyNm())
                .mdhsTelno(data.getMdhsTelno())
                .mvnPrearngeYm(data.getMvnPrearngeYm())
                .pblancUrl(data.getPblancUrl())
                .createdAt(LocalDateTime.now())
                .build();
    }
}
