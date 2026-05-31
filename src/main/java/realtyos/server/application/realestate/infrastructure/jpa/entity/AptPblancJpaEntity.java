package realtyos.server.application.realestate.infrastructure.jpa.entity;

import realtyos.server.application.common.entity.BaseEntity;
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
@Table(name = "real_estate_apt_pblanc")
public class AptPblancJpaEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String houseManageNo;
    private String pblancNo;
    private String houseNm;
    private String houseSecd;
    private String houseSecdNm;
    private String houseDtlSecd;
    private String houseDtlSecdNm;
    private String rentSecd;
    private String rentSecdNm;
    private String subscrptAreaCode;
    private String subscrptAreaCodeNm;
    private String sggCode;
    private String hssplyZip;
    private String hssplyAdres;
    private Integer totSuplyHshldco;
    private String rcritPblancDe;
    private String nsprcNm;
    private String rceptBgnde;
    private String rceptEndde;
    private String spsplyRceptBgnde;
    private String spsplyRceptEndde;
    private String gnrlRnk1CrspareaRcptde;
    private String gnrlRnk1CrspareaEndde;
    private String gnrlRnk1EtcGgRcptde;
    private String gnrlRnk1EtcGgEndde;
    private String gnrlRnk1EtcAreaRcptde;
    private String gnrlRnk1EtcAreaEndde;
    private String gnrlRnk2CrspareaRcptde;
    private String gnrlRnk2CrspareaEndde;
    private String gnrlRnk2EtcGgRcptde;
    private String gnrlRnk2EtcGgEndde;
    private String gnrlRnk2EtcAreaRcptde;
    private String gnrlRnk2EtcAreaEndde;
    private String przwnerPresnatnDe;
    private String cntrctCnclsBgnde;
    private String cntrctCnclsEndde;
    @Column(length = 1000)
    private String hmpgAdres;
    private String cnstrctEntrpsNm;
    private String mdhsTelno;
    private String bsnsMbyNm;
    private String mvnPrearngeYm;
    private String specltRdnEarthAt;
    private String mdatTrgetAreaSecd;
    private String parcprcUlsAt;
    private String imprmnBsnsAt;
    private String publicHouseEarthAt;
    private String lrsclBldlndAt;
    private String nplnPrvoprPublicHouseAt;
    private String publicHouseSpclwApplcAt;
    @Column(length = 1000)
    private String pblancUrl;
}
