package realtyos.server.application.realestate.application.service;

import realtyos.server.application.realestate.domain.SggCode;
import realtyos.server.application.realestate.domain.SggCodeRepository;
import realtyos.server.application.realestate.infrastructure.client.VworldExternalApiClient;
import realtyos.server.application.realestate.infrastructure.client.dto.VworldApiResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Slf4j
@Service
@RequiredArgsConstructor
public class SggCodeSyncService {

    private final VworldExternalApiClient vworldExternalApiClient;
    private final SggCodeRepository sggCodeRepository;

    public void syncSggCodes() {
        log.info("Vworld SggCode 데이터 수집 및 동기화 시작");

        int pageNum = 1;
        int pageSize = 1000;
        int totalSaved = 0;

        while (true) {
            VworldApiResponse response = vworldExternalApiClient.fetchSggCodes(pageNum, pageSize);

            if (response == null || response.getResponse() == null
                    || !"OK".equals(response.getResponse().getStatus())) {
                log.warn("Vworld API 응답이 없거나 상태가 정상이 아닙니다. Page: {}", pageNum);
                break;
            }

            VworldApiResponse.Result result = response.getResponse().getResult();
            if (result == null || result.getFeatureCollection() == null
                    || result.getFeatureCollection().getFeatures() == null) {
                log.warn("Vworld API 데이터 결과가 없습니다. Page: {}", pageNum);
                break;
            }

            List<SggCode> sggCodes = result.getFeatureCollection().getFeatures().stream()
                    .filter(Objects::nonNull)
                    .map(VworldApiResponse.Feature::getProperties)
                    .filter(Objects::nonNull)
                    .map(props -> SggCode.builder()
                            .sggCd(props.getSig_cd())
                            .fullNm(props.getFull_nm())
                            .sigKorNm(props.getSig_kor_nm())
                            .sigEngNm(props.getSig_eng_nm())
                            .build())
                    .toList();

            sggCodeRepository.saveAll(sggCodes);
            totalSaved += sggCodes.size();

            log.info("Vworld SggCode 데이터 저장 완료 - Page: {}, Size: {}", pageNum, sggCodes.size());

            VworldApiResponse.Page pageDetails = response.getResponse().getPage();
            if (pageDetails == null) {
                break;
            }

            int current = Integer.parseInt(pageDetails.getCurrent());
            int total = Integer.parseInt(pageDetails.getTotal());

            if (current >= total) {
                break;
            }

            pageNum++;
        }

        log.info("Vworld SggCode 데이터 수집 및 동기화 종료 - 총 {} 건 저장/수정 완료", totalSaved);
    }
}
