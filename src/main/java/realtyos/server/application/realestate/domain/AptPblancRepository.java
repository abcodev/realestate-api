package realtyos.server.application.realestate.domain;

import java.util.List;

public interface AptPblancRepository {

    AptPblanc save(AptPblanc detail);

    List<AptPblanc> saveAll(List<AptPblanc> details);

    boolean existsByPblancNo(String pblancNo);

    List<AptPblanc> findBySggCode(String sggCode);

}
