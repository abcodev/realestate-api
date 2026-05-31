package realtyos.server.application.realestate.application.service;

import realtyos.server.application.realestate.domain.AptPblanc;
import realtyos.server.application.realestate.domain.AptPblancRepository;
import realtyos.server.application.realestate.domain.DataFetchPort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class AptPblancService {

    private final DataFetchPort fetchPort;
    private final AptPblancRepository repository;

    @Transactional
    public void fetchAndSaveAptLttotPblancDetail(int page, int perPage) {
        List<AptPblanc> details = fetchPort.fetchAptLttotPblancDetail(page, perPage);

        if (details.isEmpty()) {
            log.warn("분양정보 존재하지 않음 page: {}, perPage: {}", page, perPage);
            return;
        }

        LocalDate today = LocalDate.now();

        List<AptPblanc> filteredDetails = details.stream()
                .filter(d -> isTodayOrFuture(d.rcritPblancDe(), today) || isTodayOrFuture(d.cntrctCnclsEndde(), today))
                .filter(d -> !repository.existsByPblancNo(d.pblancNo()))
                .toList();

        if (filteredDetails.isEmpty()) {
            log.info("신규 분양정보 존재하지 않음, page: {}, perPage: {}", page,
                    perPage);
            return;
        }

        repository.saveAll(filteredDetails);
        log.info("분양정보 저장 {} 건, page: {}, perPage: {}", filteredDetails.size(), page,
                perPage);
    }

    private boolean isTodayOrFuture(String dateStr, LocalDate today) {
        if (dateStr == null || dateStr.isBlank()) {
            return false;
        }
        try {
            LocalDate date = LocalDate.parse(dateStr.trim());
            return !date.isBefore(today);
        } catch (Exception e) {
            log.warn("데이터 파싱 실패 '{}', skipping.", dateStr);
            return false;
        }
    }
}
