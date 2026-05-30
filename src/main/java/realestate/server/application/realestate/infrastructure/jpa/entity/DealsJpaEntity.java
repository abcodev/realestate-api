package realestate.server.application.realestate.infrastructure.jpa.entity;

import realestate.server.application.common.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Table(name = "real_estate_deals")
public class DealsJpaEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String sggCode;
    private String umdCode;
    private String landCode;
    private String bonbun;
    private String bubun;
    private String roadName;
    private String roadNameSggCode;
    private String roadNameCode;
    private String roadNameSeq;
    private String roadNamebCode;
    private String roadNameBonbun;
    private String roadNameBubun;
    private String umdName;
    private String aptName;
    private String jibun;
    private String excluUseArea;
    private Integer dealYear;
    private Integer dealMonth;
    private Integer dealDay;
    private String dealAmount;
    private String floor;
    private String buildYear;
    private String aptSeq;
    private String cdealType;
    private String cdealDay;
    private String dealingType;
    private String estateAgentSggName;
    private String rgstDate;
    private String aptDong;
    private String slerType;
    private String buyerType;
    private String landLeaseholdType;
}
