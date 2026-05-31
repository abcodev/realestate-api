package realtyos.server.application.realestate.infrastructure.jpa.entity;

import realtyos.server.application.common.entity.BaseEntity;
import realtyos.server.application.realestate.domain.AbolishStatus;
import realtyos.server.application.realestate.domain.BgdCode;
import realtyos.server.application.realestate.infrastructure.jpa.converter.AbolishStatusConverter;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
@Table(name = "real_estate_bgd_code")
public class BgdCodeJpaEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String bgdCode;

    private String bgdName;

    @Convert(converter = AbolishStatusConverter.class)
    private AbolishStatus abolishStatus;

    public BgdCode toDomain() {
        return new BgdCode(bgdCode, bgdName);
    }

}
