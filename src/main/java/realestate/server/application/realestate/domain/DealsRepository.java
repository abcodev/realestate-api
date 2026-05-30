package realestate.server.application.realestate.domain;

import java.util.List;

public interface DealsRepository {

    void saveAll(List<Deals> deals);

    List<Deals> findByInterestRegion(RegionCode regionCode);

//    List<Deals> findByRegion1depthName(String region1depthName);
}
