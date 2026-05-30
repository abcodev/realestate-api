package realestate.server.application.realestate.infrastructure.client.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 공공데이터 포털 임대주택 모집공고 API 응답 DTO
 */
@Getter
@NoArgsConstructor
public class RentPblancApiResponse {

    // 현재 페이지
    private int page;

    // 페이지당 데이터 수
    private int perPage;

    // 전체 데이터 수
    private int totalCount;

    // 현재 페이지 데이터 수
    private int currentCount;

    // 검색 결과 수
    private int matchCount;

    // 공고 데이터 목록
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

        // 주택구분코드 (03: 공공지원민간임대)
        @JsonProperty("HOUSE_SECD")
        private String houseSecd;

        // 주택구분코드명
        @JsonProperty("HOUSE_SECD_NM")
        private String houseSecdNm;

        // 주택상세구분코드 (03: 공공지원민간임대)
        @JsonProperty("HOUSE_DETAIL_SECD")
        private String houseDetailSecd;

        // 주택상세구분코드명
        @JsonProperty("HOUSE_DETAIL_SECD_NM")
        private String houseDetailSecdNm;

        // 주택검색구분코드 (0303: 공공지원민간임대)
        @JsonProperty("SEARCH_HOUSE_SECD")
        private String searchHouseSecd;

        // 공급지역코드
        @JsonProperty("SUBSCRPT_AREA_CODE")
        private String subscrptAreaCode;

        // 공급지역명
        @JsonProperty("SUBSCRPT_AREA_CODE_NM")
        private String subscrptAreaCodeNm;

        // 모집공고일 (YYYYMMDD)
        @JsonProperty("RCRIT_PBLANC_DE")
        private String rcritPblancDe;

        // 신문사
        @JsonProperty("NSPRC_NM")
        private String nsprcNm;

        // 청약접수 시작일
        @JsonProperty("SUBSCRPT_RCEPT_BGNDE")
        private String subscrptRceptBgnde;

        // 청약접수 종료일
        @JsonProperty("SUBSCRPT_RCEPT_ENDDE")
        private String subscrptRceptEndde;

        // 당첨자 발표일
        @JsonProperty("PRZWNER_PRESNATN_DE")
        private String przwnerPresnatnDe;

        // 공급위치 우편번호
        @JsonProperty("HSSPLY_ZIP")
        private String hssplyZip;

        // 공급위치 주소
        @JsonProperty("HSSPLY_ADRES")
        private String hssplyAdres;

        // 공급 규모 (세대수)
        @JsonProperty("TOT_SUPLY_HSHLDCO")
        private int totSuplyHshldco;

        // 계약 시작일
        @JsonProperty("CNTRCT_CNCLS_BGNDE")
        private String cntrctCnclsBgnde;

        // 계약 종료일
        @JsonProperty("CNTRCT_CNCLS_ENDDE")
        private String cntrctCnclsEndde;

        // 홈페이지 주소
        @JsonProperty("HMPG_ADRES")
        private String hmpgAdres;

        // 사업주체명 (시행사)
        @JsonProperty("BSNS_MBY_NM")
        private String bsnsMbyNm;

        // 문의처 전화번호
        @JsonProperty("MDHS_TELNO")
        private String mdhsTelno;

        // 입주 예정월 (YYYYMM)
        @JsonProperty("MVN_PREARNGE_YM")
        private String mvnPrearngeYm;

        // 분양정보 URL
        @JsonProperty("PBLANC_URL")
        private String pblancUrl;
    }
}