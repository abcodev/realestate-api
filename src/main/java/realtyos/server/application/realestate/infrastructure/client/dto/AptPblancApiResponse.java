package realtyos.server.application.realestate.infrastructure.client.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 공공데이터 포털 아파트 분양공고 API 응답 DTO
 */
@Getter
@NoArgsConstructor
public class AptPblancApiResponse {

    // 페이지 번호
    private int page;

    // 페이지당 데이터 수
    private int perPage;

    // 전체 데이터 수
    private int totalCount;

    // 현재 페이지 데이터 수
    private int currentCount;

    // 검색 매칭 데이터 수
    private int matchCount;

    // 실제 분양 공고 데이터 목록
    private List<Data> data;

    @Getter
    @NoArgsConstructor
    public static class Data {

        // 주택관리번호
        @JsonProperty("HOUSE_MANAGE_NO")
        private String houseManageNo;

        // 공고번호
        @JsonProperty("PBLANC_NO")
        private String pblancNo;

        // 주택명
        @JsonProperty("HOUSE_NM")
        private String houseNm;

        // 주택구분코드 (01: APT, 09: 민간사전청약, 10: 신혼희망타운)
        @JsonProperty("HOUSE_SECD")
        private String houseSecd;

        // 주택구분코드명
        @JsonProperty("HOUSE_SECD_NM")
        private String houseSecdNm;

        // 주택상세구분코드 (01: 민영, 03: 국민)
        @JsonProperty("HOUSE_DTL_SECD")
        private String houseDtlSecd;

        // 주택상세구분코드명
        @JsonProperty("HOUSE_DTL_SECD_NM")
        private String houseDtlSecdNm;

        // 분양구분코드 (0: 분양주택, 1: 분양전환 가능임대)
        @JsonProperty("RENT_SECD")
        private String rentSecd;

        // 분양구분코드명
        @JsonProperty("RENT_SECD_NM")
        private String rentSecdNm;

        // 공급지역코드
        @JsonProperty("SUBSCRPT_AREA_CODE")
        private String subscrptAreaCode;

        // 공급지역명
        @JsonProperty("SUBSCRPT_AREA_CODE_NM")
        private String subscrptAreaCodeNm;

        // 공급위치 우편번호
        @JsonProperty("HSSPLY_ZIP")
        private String hssplyZip;

        // 공급위치 주소
        @JsonProperty("HSSPLY_ADRES")
        private String hssplyAdres;

        // 공급규모 (세대수)
        @JsonProperty("TOT_SUPLY_HSHLDCO")
        private Integer totSuplyHshldco;

        // 모집공고일
        @JsonProperty("RCRIT_PBLANC_DE")
        private String rcritPblancDe;

        // 신문사
        @JsonProperty("NSPRC_NM")
        private String nsprcNm;

        // 청약접수 시작일
        @JsonProperty("RCEPT_BGNDE")
        private String rceptBgnde;

        // 청약접수 종료일
        @JsonProperty("RCEPT_ENDDE")
        private String rceptEndde;

        // 특별공급 접수 시작일
        @JsonProperty("SPSPLY_RCEPT_BGNDE")
        private String spsplyRceptBgnde;

        // 특별공급 접수 종료일
        @JsonProperty("SPSPLY_RCEPT_ENDDE")
        private String spsplyRceptEndde;

        // 1순위 해당지역 접수 시작일
        @JsonProperty("GNRL_RNK1_CRSPAREA_RCPTDE")
        private String gnrlRnk1CrspareaRcptde;

        // 1순위 해당지역 접수 종료일
        @JsonProperty("GNRL_RNK1_CRSPAREA_ENDDE")
        private String gnrlRnk1CrspareaEndde;

        // 1순위 경기지역 접수 시작일
        @JsonProperty("GNRL_RNK1_ETC_GG_RCPTDE")
        private String gnrlRnk1EtcGgRcptde;

        // 1순위 경기지역 접수 종료일
        @JsonProperty("GNRL_RNK1_ETC_GG_ENDDE")
        private String gnrlRnk1EtcGgEndde;

        // 1순위 기타지역 접수 시작일
        @JsonProperty("GNRL_RNK1_ETC_AREA_RCPTDE")
        private String gnrlRnk1EtcAreaRcptde;

        // 1순위 기타지역 접수 종료일
        @JsonProperty("GNRL_RNK1_ETC_AREA_ENDDE")
        private String gnrlRnk1EtcAreaEndde;

        // 2순위 해당지역 접수 시작일
        @JsonProperty("GNRL_RNK2_CRSPAREA_RCPTDE")
        private String gnrlRnk2CrspareaRcptde;

        // 2순위 해당지역 접수 종료일
        @JsonProperty("GNRL_RNK2_CRSPAREA_ENDDE")
        private String gnrlRnk2CrspareaEndde;

        // 2순위 경기지역 접수 시작일
        @JsonProperty("GNRL_RNK2_ETC_GG_RCPTDE")
        private String gnrlRnk2EtcGgRcptde;

        // 2순위 경기지역 접수 종료일
        @JsonProperty("GNRL_RNK2_ETC_GG_ENDDE")
        private String gnrlRnk2EtcGgEndde;

        // 2순위 기타지역 접수 시작일
        @JsonProperty("GNRL_RNK2_ETC_AREA_RCPTDE")
        private String gnrlRnk2EtcAreaRcptde;

        // 2순위 기타지역 접수 종료일
        @JsonProperty("GNRL_RNK2_ETC_AREA_ENDDE")
        private String gnrlRnk2EtcAreaEndde;

        // 당첨자 발표일
        @JsonProperty("PRZWNER_PRESNATN_DE")
        private String przwnerPresnatnDe;

        // 계약 시작일
        @JsonProperty("CNTRCT_CNCLS_BGNDE")
        private String cntrctCnclsBgnde;

        // 계약 종료일
        @JsonProperty("CNTRCT_CNCLS_ENDDE")
        private String cntrctCnclsEndde;

        // 홈페이지 주소
        @JsonProperty("HMPG_ADRES")
        private String hmpgAdres;

        // 건설업체명 (시공사)
        @JsonProperty("CNSTRCT_ENTRPS_NM")
        private String cnstrctEntrpsNm;

        // 문의처 전화번호
        @JsonProperty("MDHS_TELNO")
        private String mdhsTelno;

        // 사업주체명 (시행사)
        @JsonProperty("BSNS_MBY_NM")
        private String bsnsMbyNm;

        // 입주 예정월
        @JsonProperty("MVN_PREARNGE_YM")
        private String mvnPrearngeYm;

        // 투기과열지구 여부
        @JsonProperty("SPECLT_RDN_EARTH_AT")
        private String specltRdnEarthAt;

        // 조정대상지역 여부
        @JsonProperty("MDAT_TRGET_AREA_SECD")
        private String mdatTrgetAreaSecd;

        // 분양가상한제 적용 여부
        @JsonProperty("PARCPRC_ULS_AT")
        private String parcprcUlsAt;

        // 정비사업 여부
        @JsonProperty("IMPRMN_BSNS_AT")
        private String imprmnBsnsAt;

        // 공공주택지구 여부
        @JsonProperty("PUBLIC_HOUSE_EARTH_AT")
        private String publicHouseEarthAt;

        // 대규모 택지개발지구 여부
        @JsonProperty("LRSCL_BLDLND_AT")
        private String lrsclBldlndAt;

        // 수도권 내 민영 공공주택지구 여부
        @JsonProperty("NPLN_PRVOPR_PUBLIC_HOUSE_AT")
        private String nplnPrvoprPublicHouseAt;

        // 공공주택 특별법 적용 여부
        @JsonProperty("PUBLIC_HOUSE_SPCLW_APPLC_AT")
        private String publicHouseSpclwApplcAt;

        // 분양정보 URL
        @JsonProperty("PBLANC_URL")
        private String pblancUrl;
    }
}