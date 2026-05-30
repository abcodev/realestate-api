package realestate.server.application.realestate.infrastructure.client.dto;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

/**
 * 공공데이터 포털 아파트매매 실거래 상세 자료 API 응답 DTO
 * API Endpoint: /getRTMSDataSvcAptTradeDev
 */
@Getter
@Setter
@ToString
@JacksonXmlRootElement(localName = "response")
public class DealsApiResponse {

    @JacksonXmlProperty(localName = "header")
    private Header header;

    @JacksonXmlProperty(localName = "body")
    private Body body;

    @Getter
    @Setter
    @ToString
    public static class Header {
        @JacksonXmlProperty(localName = "resultCode")
        private String resultCode;

        @JacksonXmlProperty(localName = "resultMsg")
        private String resultMsg;
    }

    @Getter
    @Setter
    @ToString
    public static class Body {
        @JacksonXmlElementWrapper(localName = "items")
        @JacksonXmlProperty(localName = "item")
        private List<Item> items;

        @JacksonXmlProperty(localName = "numOfRows")
        private int numOfRows;

        @JacksonXmlProperty(localName = "pageNo")
        private int pageNo;

        @JacksonXmlProperty(localName = "totalCount")
        private int totalCount;
    }

    @Getter
    @Setter
    @ToString
    public static class Item {
        @JacksonXmlProperty(localName = "sggCd")
        private String sggCd;

        @JacksonXmlProperty(localName = "umdCd")
        private String umdCd;

        @JacksonXmlProperty(localName = "landCd")
        private String landCd;

        @JacksonXmlProperty(localName = "bonbun")
        private String bonbun;

        @JacksonXmlProperty(localName = "bubun")
        private String bubun;

        @JacksonXmlProperty(localName = "roadNm")
        private String roadNm;

        @JacksonXmlProperty(localName = "roadNmSggCd")
        private String roadNmSggCd;

        @JacksonXmlProperty(localName = "roadNmCd")
        private String roadNmCd;

        @JacksonXmlProperty(localName = "roadNmSeq")
        private String roadNmSeq;

        @JacksonXmlProperty(localName = "roadNmbCd")
        private String roadNmbCd;

        @JacksonXmlProperty(localName = "roadNmBonbun")
        private String roadNmBonbun;

        @JacksonXmlProperty(localName = "roadNmBubun")
        private String roadNmBubun;

        @JacksonXmlProperty(localName = "umdNm")
        private String umdNm;

        @JacksonXmlProperty(localName = "aptNm")
        private String aptNm;

        @JacksonXmlProperty(localName = "jibun")
        private String jibun;

        @JacksonXmlProperty(localName = "excluUseAr")
        private String excluUseAr;

        @JacksonXmlProperty(localName = "dealYear")
        private Integer dealYear;

        @JacksonXmlProperty(localName = "dealMonth")
        private Integer dealMonth;

        @JacksonXmlProperty(localName = "dealDay")
        private Integer dealDay;

        @JacksonXmlProperty(localName = "dealAmount")
        private String dealAmount;

        @JacksonXmlProperty(localName = "floor")
        private String floor;

        @JacksonXmlProperty(localName = "buildYear")
        private String buildYear;

        @JacksonXmlProperty(localName = "aptSeq")
        private String aptSeq;

        @JacksonXmlProperty(localName = "cdealType")
        private String cdealType;

        @JacksonXmlProperty(localName = "cdealDay")
        private String cdealDay;

        @JacksonXmlProperty(localName = "dealingGbn")
        private String dealingGbn;

        @JacksonXmlProperty(localName = "estateAgentSggNm")
        private String estateAgentSggNm;

        @JacksonXmlProperty(localName = "rgstDate")
        private String rgstDate;

        @JacksonXmlProperty(localName = "aptDong")
        private String aptDong;

        @JacksonXmlProperty(localName = "slerGbn")
        private String slerGbn;

        @JacksonXmlProperty(localName = "buyerGbn")
        private String buyerGbn;

        @JacksonXmlProperty(localName = "landLeaseholdGbn")
        private String landLeaseholdGbn;
    }
}
