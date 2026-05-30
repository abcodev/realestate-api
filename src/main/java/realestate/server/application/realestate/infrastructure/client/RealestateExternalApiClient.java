package realestate.server.application.realestate.infrastructure.client;

import realestate.server.application.realestate.infrastructure.client.dto.DealsApiResponse;
import realestate.server.application.realestate.infrastructure.client.dto.AptPblancApiResponse;
import realestate.server.application.realestate.infrastructure.client.dto.RentPblancApiResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@Slf4j
@Component
@RequiredArgsConstructor
public class RealestateExternalApiClient {

        private final RestClient.Builder restClientBuilder;

        @Value("${external.api.realestate.service-key}")
        private String serviceKey;

        @Value("${external.api.realestate.deals-base-url}")
        private String dealsBaseUrl;

        @Value("${external.api.realestate.pblanc-base-url}")
        private String pblancBaseUrl;

//        public DealsApiResponse fetchDeals(String lawdCd, String dealYmd, int pageNo, int numOfRows) {
//                RestClient restClient = restClientBuilder.build();
//
//                URI uri = UriComponentsBuilder.fromUriString(dealsBaseUrl + "/RTMSDataSvcAptTrade/getRTMSDataSvcAptTrade")
//                                .queryParam("serviceKey", serviceKey)
//                                .queryParam("LAWD_CD", lawdCd)
//                                .queryParam("DEAL_YMD", dealYmd)
//                                .queryParam("pageNo", pageNo)
//                                .queryParam("numOfRows", numOfRows)
//                                .build()
//                                .toUri();
//
//                log.info("Fetching real estate deals from: {}", uri);
//
//                return restClient.get()
//                                .uri(uri)
//                                .accept(MediaType.APPLICATION_XML, MediaType.TEXT_XML)
//                                .retrieve()
//                                .body(DealsApiResponse.class);
//        }

        public DealsApiResponse fetchDeals(String lawdCd, String dealYmd, int pageNo, int numOfRows) {
                RestClient restClient = restClientBuilder.build();

                URI uri = UriComponentsBuilder.fromUriString(dealsBaseUrl + "/RTMSDataSvcAptTradeDev/getRTMSDataSvcAptTradeDev")
                                .queryParam("serviceKey", serviceKey)
                                .queryParam("LAWD_CD", lawdCd)
                                .queryParam("DEAL_YMD", dealYmd)
                                .queryParam("pageNo", pageNo)
                                .queryParam("numOfRows", numOfRows)
                                .build()
                                .toUri();

                log.info("Fetching real estate deals detail from: {}", uri);

                return restClient.get()
                                .uri(uri)
                                .accept(MediaType.APPLICATION_XML, MediaType.TEXT_XML)
                                .retrieve()
                                .body(DealsApiResponse.class);
        }

        public AptPblancApiResponse fetchAptLttotPblancDetail(int page, int perPage) {
                RestClient restClient = restClientBuilder.build();

                URI uri = UriComponentsBuilder
                                .fromUriString(pblancBaseUrl + "/getAPTLttotPblancDetail")
                                .queryParam("page", page)
                                .queryParam("perPage", perPage)
                                .queryParam("serviceKey", serviceKey)
                                .build()
                                .toUri();

                log.info("Fetching APT Lttot Pblanc Detail from: {}", uri);

                return restClient.get()
                                .uri(uri)
                                .accept(MediaType.APPLICATION_JSON)
                                .retrieve()
                                .body(AptPblancApiResponse.class);
        }

        public RentPblancApiResponse fetchPblPvtRentLttotPblancDetail(int page, int perPage) {
                RestClient restClient = restClientBuilder.build();

                URI uri = UriComponentsBuilder.fromUriString(
                                pblancBaseUrl + "/getPblPvtRentLttotPblancDetail")
                                .queryParam("page", page)
                                .queryParam("perPage", perPage)
                                .queryParam("serviceKey", serviceKey)
                                .build()
                                .toUri();

                log.info("Fetching Pbl Pvt Rent Lttot Pblanc Detail from: {}", uri);

                return restClient.get()
                                .uri(uri)
                                .accept(MediaType.APPLICATION_JSON)
                                .retrieve()
                                .body(RentPblancApiResponse.class);
        }
}
