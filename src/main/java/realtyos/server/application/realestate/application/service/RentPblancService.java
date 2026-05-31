package realtyos.server.application.realestate.application.service;

import realtyos.server.application.realestate.domain.RentPblanc;
import realtyos.server.application.realestate.domain.RentPblancRepository;
import realtyos.server.application.realestate.domain.DataFetchPort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class RentPblancService {

    private final DataFetchPort fetchPort;
    private final RentPblancRepository repository;

    @Transactional
    public void fetchAndSavePblPvtRentLttotPblancDetail(int page, int perPage) {
        List<RentPblanc> details = fetchPort.fetchPblPvtRentLttotPblancDetail(page, perPage);

        if (details.isEmpty()) {
            log.warn("No data found for Pbl Pvt Rent Lttot Pblanc Detail page: {}, perPage: {}", page, perPage);
            return;
        }

        LocalDate today = LocalDate.now();

        List<RentPblanc> filteredDetails = details.stream()
                .filter(d -> isTodayOrFuture(d.rcritPblancDe(), today) || isTodayOrFuture(d.cntrctCnclsEndde(), today))
                .filter(d -> !repository.existsByPblancNo(d.pblancNo()))
                .toList();

        if (filteredDetails.isEmpty()) {
            log.info("No new or valid Pbl Pvt Rent Lttot Pblanc Detail records to save for page: {}, perPage: {}", page,
                    perPage);
            return;
        }

        repository.saveAll(filteredDetails);
        log.info("Saved {} Pbl Pvt Rent Lttot Pblanc Detail records for page: {}, perPage: {}", filteredDetails.size(),
                page,
                perPage);
    }

    private boolean isTodayOrFuture(String dateStr, LocalDate today) {
        if (dateStr == null || dateStr.isBlank()) {
            return false;
        }
        try {
            String sanitized = dateStr.trim();
            LocalDate date;
            if (sanitized.length() == 8) {
                date = LocalDate.parse(sanitized, DateTimeFormatter.ofPattern("yyyyMMdd"));
            } else {
                date = LocalDate.parse(sanitized);
            }
            return !date.isBefore(today);
        } catch (Exception e) {
            log.warn("Failed to parse date '{}', skipping.", dateStr);
            return false;
        }
    }
}
