package realestate.server.application.realestate.infrastructure.jpa.entity;

import realestate.server.application.common.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
@Table(name = "real_estate_rent_pblanc")
public class RentPblancJpaEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String houseManageNo;
    private String pblancNo;
    private String houseNm;
    private String houseSecd;
    private String houseSecdNm;
    private String houseDetailSecd;
    private String houseDetailSecdNm;
    private String searchHouseSecd;
    private String subscrptAreaCode;
    private String subscrptAreaCodeNm;
    private String sggCode;
    private String rcritPblancDe;
    private String nsprcNm;
    private String subscrptRceptBgnde;
    private String subscrptRceptEndde;
    private String przwnerPresnatnDe;
    private String hssplyZip;
    private String hssplyAdres;
    private Integer totSuplyHshldco;
    private String cntrctCnclsBgnde;
    private String cntrctCnclsEndde;
    @Column(length = 1000)
    private String hmpgAdres;
    private String bsnsMbyNm;
    private String mdhsTelno;
    private String mvnPrearngeYm;
    @Column(length = 1000)
    private String pblancUrl;
}
