package realtyos.server.application.realestate.domain;

import java.util.List;
import java.util.Optional;

public interface BgdCodeRepository {

    List<String> findDistinctBgdCodes();

    List<BgdCode> findByBgdNameContainingAndNot1Depth(String regionName);

    Optional<String> findUmdCodePrefixByRegionName(String regionName);

    Optional<BgdCode> findByBgdCode(String bgdCode);

}
