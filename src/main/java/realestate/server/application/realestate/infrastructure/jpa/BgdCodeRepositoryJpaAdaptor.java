package realestate.server.application.realestate.infrastructure.jpa;

import realestate.server.application.realestate.domain.BgdCode;
import realestate.server.application.realestate.domain.BgdCodeRepository;
import realestate.server.application.realestate.infrastructure.jpa.entity.BgdCodeJpaEntity;
import realestate.server.application.realestate.infrastructure.jpa.repository.BgdCodeJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BgdCodeRepositoryJpaAdaptor implements BgdCodeRepository {

    private final BgdCodeJpaRepository jpaRepository;

    @Override
    public List<String> findDistinctBgdCodes() {
        return jpaRepository.findDistinctBgdCodes();
    }

    @Override
    public List<BgdCode> findByBgdNameContainingAndNot1Depth(String regionName) {
        return jpaRepository.searchByRegionNameExcluding1Depth(regionName).stream()
                .map(BgdCodeJpaEntity::toDomain)
                .toList();
    }

    @Override
    public Optional<String> findUmdCodePrefixByRegionName(String regionName) {
        return jpaRepository.findUmdCodePrefixByRegionName(regionName);
    }

    @Override
    public Optional<BgdCode> findByBgdCode(String bgdCode) {
        return jpaRepository.findByBgdCode(bgdCode)
                .map(BgdCodeJpaEntity::toDomain);
    }

}
