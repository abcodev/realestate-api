package realestate.server.application.realestate.application.service;

import realestate.server.application.realestate.domain.SggCodeRepository;
import realestate.server.application.realestate.infrastructure.jpa.repository.SggCodeJpaRepository;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ActiveProfiles("loc")
class SggCodeSyncServiceTest {

    @Autowired
    private SggCodeSyncService sggCodeSyncService;

    @Autowired
    private SggCodeJpaRepository sggCodeJpaRepository;

    @Test
    @DisplayName("Vworld API로부터 실제 데이터를 수집해서 DB에 저장한다")
    void fetchAndSaveSggCodesFromVworldAPI() {
        // given
        long initialCount = sggCodeJpaRepository.count();

        // when
        sggCodeSyncService.syncSggCodes();

        // then
        long finalCount = sggCodeJpaRepository.count();
        System.out.println("초기 데이터 수: " + initialCount);
        System.out.println("API 호출 후 데이터 수: " + finalCount);

        assertThat(finalCount).isGreaterThan(0);
    }
}
