package realestate.server.application.realestate.infrastructure.jpa;

import realestate.server.application.realestate.domain.SggCode;
import realestate.server.application.realestate.domain.SggCodeRepository;
import realestate.server.application.realestate.infrastructure.jpa.entity.SggCodeJpaEntity;
import realestate.server.application.realestate.infrastructure.jpa.mapper.SggCodeMapper;
import realestate.server.application.realestate.infrastructure.jpa.repository.SggCodeJpaRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Repository
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class SggCodeRepositoryJpaAdaptor implements SggCodeRepository {

    private final SggCodeJpaRepository jpaRepository;
    private final SggCodeMapper mapper;

    @Transactional
    @Override
    public void saveAll(List<SggCode> sggCodes) {
        if (sggCodes.isEmpty()) {
            return;
        }

        List<SggCodeJpaEntity> entitiesToSave = new java.util.ArrayList<>();
        int newCount = 0;
        int updatedCount = 0;

        for (SggCode code : sggCodes) {
            SggCodeJpaEntity existing = jpaRepository.findBySggCd(code.sggCd()).orElse(null);

            if (existing != null) {
                mapper.updateEntityFromDomain(code, existing);
                entitiesToSave.add(existing);
                updatedCount++;
            } else {
                entitiesToSave.add(mapper.mapToJpaEntity(code));
                newCount++;
            }
        }

        log.info("Upsert summary (SggCode) - New: {}, Updated: {}", newCount, updatedCount);
        jpaRepository.saveAll(entitiesToSave);
    }
}
