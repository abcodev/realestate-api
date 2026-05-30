package realestate.server.application.realestate.interfaces.scheduler;

import realestate.server.application.realestate.application.service.DealsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class DealsBatchScheduler {

    private final DealsService dealsService;

    @Scheduled(cron = "0 3 3 * * ?", zone = "Asia/Seoul")
    public void scheduleDailyDealsFetch() {
        log.info("실거래 데이터 배치 수집 시작");

        try {
            dealsService.fetchAndSaveDeals();
            log.info("실거래 데이터 배치 수집 완료");
        } catch (Exception e) {
            log.error("실거래 데이터 배치 수집 중 오류 발생", e);
        }
    }
}
