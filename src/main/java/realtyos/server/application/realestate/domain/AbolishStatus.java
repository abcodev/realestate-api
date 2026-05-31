package realtyos.server.application.realestate.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum AbolishStatus {
    EXIST("존재"),
    ABOLISHED("폐지");

    private final String description;
}
