package realtyos.server.application.realestate.domain;

import java.util.List;

public interface DealsRepository {

    void saveAll(List<Deals> deals);
}
