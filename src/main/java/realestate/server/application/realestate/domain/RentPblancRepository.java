package realestate.server.application.realestate.domain;

import java.util.List;

public interface RentPblancRepository {
    RentPblanc save(RentPblanc detail);

    List<RentPblanc> saveAll(List<RentPblanc> details);

    boolean existsByPblancNo(String umdCode);

    List<RentPblanc> findBySggCode(String umdCode);
}
