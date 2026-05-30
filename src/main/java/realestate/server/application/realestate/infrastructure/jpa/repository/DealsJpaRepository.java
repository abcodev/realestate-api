package realestate.server.application.realestate.infrastructure.jpa.repository;

import realestate.server.application.realestate.domain.RegionCode;
import realestate.server.application.realestate.infrastructure.jpa.entity.DealsJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import org.springframework.data.domain.Pageable;
import java.util.List;

public interface DealsJpaRepository extends JpaRepository<DealsJpaEntity, Long> {

        List<DealsJpaEntity> findBySggCodeAndDealYearAndDealMonth(String sggCode, Integer dealYear, Integer dealMonth);

        @Query("select d from DealsJpaEntity d " +
                "where d.sggCode like concat(trim(trailing '0' from :#{#regionCode.sggCode}), '%') " +
                "and (" +
                "    :#{#regionCode.umdCode} = '00000' " +
                "    or d.umdCode like concat(trim(trailing '0' from :#{#regionCode.umdCode}), '%')" +
                ") " +
                "order by cast(d.dealYear as int) desc, cast(d.dealMonth as int) desc, cast(d.dealDay as int) desc")
        List<DealsJpaEntity> findAllByRegionCode(@Param("regionCode") RegionCode regionCode, Pageable pageable);

//        List<DealsJpaEntity> findTop3ByRegion1depthNameOrderByDealYearDescDealMonthDescDealDayDesc(String region1depthName);

}
