package realtyos.server.application.realestate.infrastructure.jpa.entity;

import realtyos.server.application.common.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import realtyos.server.application.realestate.domain.AbolishStatus;
import realtyos.server.application.realestate.infrastructure.jpa.converter.AbolishStatusConverter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
@Table(name = "real_estate_land_area")
public class LandAreaJpaEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String landAreaCode;

    private String landAreaName;

    @Convert(converter = AbolishStatusConverter.class)
    private AbolishStatus abolishStatus;

}
