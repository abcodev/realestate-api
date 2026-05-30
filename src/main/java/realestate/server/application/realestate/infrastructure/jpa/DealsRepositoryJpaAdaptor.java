package realestate.server.application.realestate.infrastructure.jpa;

import realestate.server.application.realestate.domain.Deals;
import realestate.server.application.realestate.domain.DealsRepository;
import realestate.server.application.realestate.domain.RegionCode;
import realestate.server.application.realestate.infrastructure.jpa.entity.DealsJpaEntity;
import realestate.server.application.realestate.infrastructure.jpa.mapper.DealsMapper;
import realestate.server.application.realestate.infrastructure.jpa.repository.DealsJpaRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Repository
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class DealsRepositoryJpaAdaptor implements DealsRepository {

    private final DealsJpaRepository jpaRepository;
    private final DealsMapper mapper;

    @Transactional
    @Override
    public void saveAll(List<Deals> deals) {

        if (deals.isEmpty()) {
            return;
        }

        Map<String, List<Deals>> groupedDeals = deals.stream()
                .collect(Collectors.groupingBy(d ->
                        d.sggCode() + "-" + d.dealYear() + "-" + d.dealMonth()
                ));

        List<DealsJpaEntity> entitiesToSave = new java.util.ArrayList<>();

        int newCount = 0;
        int updatedCount = 0;

        for (List<Deals> batch : groupedDeals.values()) {

            Deals sample = batch.getFirst();

            List<DealsJpaEntity> existingDeals =
                    jpaRepository.findBySggCodeAndDealYearAndDealMonth(
                            sample.sggCode(),
                            sample.dealYear(),
                            sample.dealMonth()
                    );

            Map<DealKey, DealsJpaEntity> existingMap =
                    existingDeals.stream()
                            .collect(Collectors.toMap(
                                    this::buildKey,
                                    e -> e,
                                    (a, b) -> a
                            ));

            for (Deals deal : batch) {

                DealKey key = buildKey(deal);

                DealsJpaEntity existing = existingMap.get(key);

                if (existing != null) {
                    mapper.updateEntityFromDomain(deal, existing);
                    entitiesToSave.add(existing);
                    updatedCount++;
                } else {
                    entitiesToSave.add(mapper.mapToJpaEntity(deal));
                    newCount++;
                }
            }
        }

        log.info("Upsert summary - New: {}, Updated: {}", newCount, updatedCount);

        jpaRepository.saveAll(entitiesToSave);
    }

    private record DealKey(
            String sggCode,
            Integer dealYear,
            Integer dealMonth,
            Integer dealDay,
            String aptName,
            String floor,
            String umdName,
            String excluUseArea,
            String jibun,
            String dealAmount) {}

    private DealKey buildKey(Deals d) {
        return new DealKey(
                d.sggCode(),
                d.dealYear(),
                d.dealMonth(),
                d.dealDay(),
                d.aptName(),
                d.floor(),
                d.umdName(),
                d.excluUseArea(),
                d.jibun(),
                d.dealAmount()
        );
    }

    private DealKey buildKey(DealsJpaEntity e) {
        return new DealKey(
                e.getSggCode(),
                e.getDealYear(),
                e.getDealMonth(),
                e.getDealDay(),
                e.getAptName(),
                e.getFloor(),
                e.getUmdName(),
                e.getExcluUseArea(),
                e.getJibun(),
                e.getDealAmount()
        );
    }


    @Override
    public List<Deals> findByInterestRegion(RegionCode regionCode) {
        Pageable page = PageRequest.of(0, 3);
        return jpaRepository.findAllByRegionCode(regionCode, page)
                .stream()
                .map(mapper::mapToDomain)
                .toList();
    }

//    @Override
//    public List<Deals> findByRegion1depthName(String region1depthName) {
//        return jpaRepository.findTop3ByRegion1depthNameOrderByDealYearDescDealMonthDescDealDayDesc(region1depthName)
//                .stream()
//                .map(mapper::mapToDomain)
//                .toList();
//    }

}
