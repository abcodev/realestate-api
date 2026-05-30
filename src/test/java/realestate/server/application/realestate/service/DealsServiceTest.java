package realestate.server.application.realestate.service;

import realestate.server.application.realestate.application.service.DealsService;
import realestate.server.application.realestate.infrastructure.jpa.repository.DealsJpaRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class DealsServiceTest {

    @Autowired
    private DealsService dealsService;

    @Autowired
    private DealsJpaRepository dealsRepository;

    @Test
    void 실거래가_데이터를_조회하여_저장하면_데이터_개수가_증가하거나_유지된다() {
        long initialCount = dealsRepository.count();

        dealsService.fetchAndSaveDeals();

        long newCount = dealsRepository.count();
        assertThat(newCount).isGreaterThanOrEqualTo(initialCount);
    }
}