package realtyos.server.application.realestate.service;

import realtyos.server.application.realestate.application.service.AptPblancService;
import realtyos.server.application.realestate.application.service.RentPblancService;
import realtyos.server.application.realestate.infrastructure.jpa.repository.AptPblancJpaRepository;
import realtyos.server.application.realestate.infrastructure.jpa.repository.RentPblancJpaRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class PblancIntegrationTest {

    @Autowired
    private AptPblancService aptServer;

    @Autowired
    private RentPblancService rentServer;

    @Autowired
    private AptPblancJpaRepository aptRepository;

    @Autowired
    private RentPblancJpaRepository rentRepository;

    @Test
    void 아파트_분양공고_데이터를_조회하여_저장하면_데이터_개수가_증가하거나_유지된다() {
        long initialCount = aptRepository.count();

        aptServer.fetchAndSaveAptLttotPblancDetail(1, 10);

        long newCount = aptRepository.count();
        assertThat(newCount).isGreaterThanOrEqualTo(initialCount);
    }

    @Test
    void 임대_분양공고_데이터를_조회하여_저장하면_데이터_개수가_증가하거나_유지된다() {
        long initialCount = rentRepository.count();

        rentServer.fetchAndSavePblPvtRentLttotPblancDetail(1, 10);

        long newCount = rentRepository.count();
        assertThat(newCount).isGreaterThanOrEqualTo(initialCount);
    }

}