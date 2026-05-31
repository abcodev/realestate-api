package realtyos.server.application.realestate.infrastructure.jpa;

import realtyos.server.application.realestate.domain.AptPblanc;
import realtyos.server.application.realestate.domain.AptPblancRepository;
import realtyos.server.application.realestate.infrastructure.jpa.entity.AptPblancJpaEntity;
import realtyos.server.application.realestate.infrastructure.jpa.mapper.AptPblancMapper;
import realtyos.server.application.realestate.infrastructure.jpa.repository.AptPblancJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Repository
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AptPblancRepositoryJpaAdaptor implements AptPblancRepository {

    private final AptPblancJpaRepository jpaRepository;
    private final AptPblancMapper mapper;

    @Override
    @Transactional
    public AptPblanc save(AptPblanc detail) {
        return mapper.toDomain(jpaRepository.save(mapper.toEntity(detail)));
    }

    @Override
    @Transactional
    public List<AptPblanc> saveAll(List<AptPblanc> details) {
        List<AptPblancJpaEntity> entities = details.stream()
                .map(mapper::toEntity)
                .toList();
        return jpaRepository.saveAll(entities).stream()
                .map(mapper::toDomain)
                .toList();
    }

    @Override
    public boolean existsByPblancNo(String pblancNo) {
        return jpaRepository.existsByPblancNo(pblancNo);
    }

    @Override
    public List<AptPblanc> findBySggCode(String sggCode) {
        LocalDate today = LocalDate.now();
        LocalDate sevenDaysAgo = today.minusDays(7);

        return jpaRepository.findValidApts(sggCode, sevenDaysAgo.toString(), today.toString())
                .stream()
                .map(mapper::toDomain)
                .toList();
    }
}
