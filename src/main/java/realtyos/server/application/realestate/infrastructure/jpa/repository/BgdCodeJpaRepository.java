package realtyos.server.application.realestate.infrastructure.jpa.repository;

import realtyos.server.application.realestate.infrastructure.jpa.entity.BgdCodeJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface BgdCodeJpaRepository extends JpaRepository<BgdCodeJpaEntity, Long> {

    @Query("SELECT DISTINCT SUBSTRING(b.bgdCode, 1, 5) FROM BgdCodeJpaEntity b")
    List<String> findDistinctBgdCodes();

    @Query(" SELECT b FROM BgdCodeJpaEntity b WHERE b.bgdCode NOT LIKE '__00000000' AND (b.bgdName LIKE CONCAT(:regionName, '%')OR b.bgdName LIKE CONCAT('% ', :regionName, '%') OR b.bgdName LIKE CONCAT('%', :regionName, '%')) ORDER BY CASE WHEN b.bgdName LIKE CONCAT(:regionName, '%') THEN 0 ELSE 1 END, CASE WHEN b.bgdCode LIKE '__00000000' THEN 1 WHEN b.bgdCode LIKE '____000000' THEN 2 WHEN b.bgdCode LIKE '______0000' THEN 3 ELSE 4 END ")
    List<BgdCodeJpaEntity> searchByRegionNameExcluding1Depth(@Param("regionName") String regionName);

    @Query("SELECT SUBSTRING(b.bgdCode, 1, 5) FROM BgdCodeJpaEntity b WHERE b.bgdName LIKE :regionName% AND b.bgdCode LIKE '%00000000'")
    Optional<String> findUmdCodePrefixByRegionName(@Param("regionName") String regionName);

    Optional<BgdCodeJpaEntity> findByBgdCode(String bgdCode);

}
