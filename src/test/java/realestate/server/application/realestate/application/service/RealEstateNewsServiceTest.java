package realestate.server.application.realestate.application.service;

import realestate.server.application.realestate.domain.RealEstateNewsSummary;
import realestate.server.application.realestate.domain.RealEstateNewsSummaryRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class RealEstateNewsServiceTest {

    @Autowired
    private RealEstateNewsService realEstateNewsService;

    @Autowired
    private RealEstateNewsSummaryRepository newsSummaryRepository;

    @Test
    @DisplayName("한국경제 RSS를 가져와서 24시간 내 기사를 긁은 후, AI가 브리핑을 잘 생성해서 DB에 저장하는지 확인한다.")
    void processDailyRealEstateNews_integration_test() {
        // given & when (실제 배치 로직 호출 - !! OpenAI/Gemini API KEY가 설정되어 있어야 합니다)
        realEstateNewsService.processDailyRealEstateNews();

        // then (DB에 오늘 날짜로 최신 저장본이 있는지 확인)
        Optional<RealEstateNewsSummary> latestOpt = newsSummaryRepository.findLatest();
        
        assertThat(latestOpt).isPresent();
        RealEstateNewsSummary summary = latestOpt.get();
        
        System.out.println("====== AI 요약 결과 확인 ======");
        System.out.println("요약일: " + summary.summaryDate());
        System.out.println("한줄 브리핑: " + summary.oneLineBrief());
        System.out.println("상세 브리핑:\n" + summary.summary());
        System.out.println("=============================");
        
        assertThat(summary.oneLineBrief()).isNotBlank();
        assertThat(summary.summary()).isNotBlank();
    }
}
