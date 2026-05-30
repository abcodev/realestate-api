package realestate.server.application.realestate.interfaces.scheduler;

import realestate.server.application.realestate.application.service.AptPblancService;
import realestate.server.application.realestate.application.service.RentPblancService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class PblancBatchScheduler {

    private final AptPblancService aptService;
    private final RentPblancService rentService;

    @Scheduled(cron = "0 3 1 * * ?", zone = "Asia/Seoul")
    public void scheduleDailyPblancFetch() {
        log.info("분양, 임대 데이터 수집 시작");

        try {
            aptService.fetchAndSaveAptLttotPblancDetail(1, 100);
            log.info("분양 데이터 저장 성공");
        } catch (Exception e) {
            log.error("분양 데이터 저장 에러", e);
        }

        try {
            rentService.fetchAndSavePblPvtRentLttotPblancDetail(1, 100);
            log.info("임대 데이터 저장 성공");
        } catch (Exception e) {
            log.error("임대 데이터 저장 에러", e);
        }

        log.info("분양, 임대 배치 실행 완료");
    }
}
