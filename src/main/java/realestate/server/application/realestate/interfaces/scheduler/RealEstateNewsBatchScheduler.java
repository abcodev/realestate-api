package realestate.server.application.realestate.interfaces.scheduler;

import realestate.server.application.realestate.application.service.RealEstateNewsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class RealEstateNewsBatchScheduler {

    private final RealEstateNewsService realEstateNewsService;

    /**
     * 매일 오전 5시 33분에 한국경제 부동산 뉴스를 스크래핑하고 AI로 요약하여 DB에 저장합니다.
     */
    @Scheduled(cron = "0 33 5 * * ?", zone = "Asia/Seoul")
    public void executeDailyRealEstateNewsSummary() {
        log.info("[Scheduler] 아침 5:33 부동산 뉴스 AI 요약 배치 시작");
        try {
            realEstateNewsService.processDailyRealEstateNews();
            log.info("[Scheduler] 아침 5:33 부동산 뉴스 AI 요약 배치 성공");
        } catch (Exception e) {
            log.error("[Scheduler] 아침 5:33 부동산 뉴스 AI 요 요약 배치 실패", e);
        }
    }
}
