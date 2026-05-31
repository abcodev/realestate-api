package realtyos.server.application.realestate.infrastructure.jpa;

import realtyos.server.application.realestate.domain.RentPblanc;
import realtyos.server.application.realestate.domain.RentPblancRepository;
import realtyos.server.application.realestate.infrastructure.jpa.entity.RentPblancJpaEntity;
import realtyos.server.application.realestate.infrastructure.jpa.repository.RentPblancJpaRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Slf4j
@Repository
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class RentPblancRepositoryJpaAdaptor implements RentPblancRepository {

    private final RentPblancJpaRepository jpaRepository;

    @Override
    @Transactional
    public RentPblanc save(RentPblanc detail) {
        RentPblancJpaEntity entity = mapToEntity(detail);
        return mapToDomain(jpaRepository.save(entity));
    }

    @Override
    @Transactional
    public List<RentPblanc> saveAll(List<RentPblanc> details) {
        List<RentPblancJpaEntity> entities = details.stream().map(this::mapToEntity).toList();
        return jpaRepository.saveAll(entities).stream().map(this::mapToDomain).toList();
    }

    @Override
    public boolean existsByPblancNo(String pblancNo) {
        return jpaRepository.existsByPblancNo(pblancNo);
    }

    @Override
    public List<RentPblanc> findBySggCode(String sggCode) {
        LocalDate today = LocalDate.now();
        LocalDate sevenDaysAgo = today.minusDays(7);

        return jpaRepository.findValidRents(sggCode, sevenDaysAgo.toString(), today.toString())
                .stream()
                .map(this::mapToDomain)
                .toList();
    }

    private RentPblancJpaEntity mapToEntity(RentPblanc domain) {
        return RentPblancJpaEntity.builder()
                .id(domain.id())
                .houseManageNo(domain.houseManageNo())
                .pblancNo(domain.pblancNo())
                .houseNm(domain.houseNm())
                .houseSecd(domain.houseSecd())
                .houseSecdNm(domain.houseSecdNm())
                .houseDetailSecd(domain.houseDetailSecd())
                .houseDetailSecdNm(domain.houseDetailSecdNm())
                .searchHouseSecd(domain.searchHouseSecd())
                .subscrptAreaCode(domain.subscrptAreaCode())
                .subscrptAreaCodeNm(domain.subscrptAreaCodeNm())
                .sggCode(domain.sggCode())
                .rcritPblancDe(domain.rcritPblancDe())
                .nsprcNm(domain.nsprcNm())
                .subscrptRceptBgnde(domain.subscrptRceptBgnde())
                .subscrptRceptEndde(domain.subscrptRceptEndde())
                .przwnerPresnatnDe(domain.przwnerPresnatnDe())
                .hssplyZip(domain.hssplyZip())
                .hssplyAdres(domain.hssplyAdres())
                .totSuplyHshldco(domain.totSuplyHshldco())
                .cntrctCnclsBgnde(domain.cntrctCnclsBgnde())
                .cntrctCnclsEndde(domain.cntrctCnclsEndde())
                .hmpgAdres(domain.hmpgAdres())
                .bsnsMbyNm(domain.bsnsMbyNm())
                .mdhsTelno(domain.mdhsTelno())
                .mvnPrearngeYm(domain.mvnPrearngeYm())
                .pblancUrl(domain.pblancUrl())
                .build();
    }

    private RentPblanc mapToDomain(RentPblancJpaEntity entity) {
        return RentPblanc.builder()
                .id(entity.getId())
                .houseManageNo(entity.getHouseManageNo())
                .pblancNo(entity.getPblancNo())
                .houseNm(entity.getHouseNm())
                .houseSecd(entity.getHouseSecd())
                .houseSecdNm(entity.getHouseSecdNm())
                .houseDetailSecd(entity.getHouseDetailSecd())
                .houseDetailSecdNm(entity.getHouseDetailSecdNm())
                .searchHouseSecd(entity.getSearchHouseSecd())
                .subscrptAreaCode(entity.getSubscrptAreaCode())
                .subscrptAreaCodeNm(entity.getSubscrptAreaCodeNm())
                .sggCode(entity.getSggCode())
                .rcritPblancDe(entity.getRcritPblancDe())
                .nsprcNm(entity.getNsprcNm())
                .subscrptRceptBgnde(entity.getSubscrptRceptBgnde())
                .subscrptRceptEndde(entity.getSubscrptRceptEndde())
                .przwnerPresnatnDe(entity.getPrzwnerPresnatnDe())
                .hssplyZip(entity.getHssplyZip())
                .hssplyAdres(entity.getHssplyAdres())
                .totSuplyHshldco(entity.getTotSuplyHshldco())
                .cntrctCnclsBgnde(entity.getCntrctCnclsBgnde())
                .cntrctCnclsEndde(entity.getCntrctCnclsEndde())
                .hmpgAdres(entity.getHmpgAdres())
                .bsnsMbyNm(entity.getBsnsMbyNm())
                .mdhsTelno(entity.getMdhsTelno())
                .mvnPrearngeYm(entity.getMvnPrearngeYm())
                .pblancUrl(entity.getPblancUrl())
                .build();
    }
}
