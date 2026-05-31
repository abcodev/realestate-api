package realtyos.server.application.realestate.domain;

import java.util.List;

public interface DataFetchPort {

    List<Deals> fetchDeals(String lawdCd, String dealYmd);

    List<AptPblanc> fetchAptLttotPblancDetail(int page, int perPage);

    List<RentPblanc> fetchPblPvtRentLttotPblancDetail(int page, int perPage);
}
