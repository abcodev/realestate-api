package realtyos.server.application.realestate.infrastructure.client;

import realtyos.server.application.realestate.infrastructure.client.dto.VworldApiResponse;
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
public class VworldExternalApiClient {

    private final RestClient.Builder restClientBuilder;

    @Value("${external.api.realestate.vworld-service-key}")
    private String vworldServiceKey;

    public VworldApiResponse fetchSggCodes(int page, int size) {
        RestClient restClient = restClientBuilder.build();

        URI uri = UriComponentsBuilder.fromUriString("https://api.vworld.kr/req/data")
                .queryParam("service", "data")
                .queryParam("request", "GetFeature")
                .queryParam("data", "LT_C_ADSIGG_INFO")
                .queryParam("key", vworldServiceKey)
                .queryParam("format", "json")
                .queryParam("geomFilter", "BOX(124,33,132,39)")
                .queryParam("page", page)
                .queryParam("size", size)
                .build()
                .toUri();

        log.info("Fetching Vworld SggCode data from: {}", uri);

        return restClient.get()
                .uri(uri)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .body(VworldApiResponse.class);
    }
}
