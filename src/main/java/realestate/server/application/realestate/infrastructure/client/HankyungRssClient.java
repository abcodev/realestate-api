package realestate.server.application.realestate.infrastructure.client;

import realestate.server.application.realestate.infrastructure.client.dto.RssFeedResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

@Component
public class HankyungRssClient {

    private final RestClient restClient;
    private static final String RSS_URL = "https://www.hankyung.com/feed/realestate";

    public HankyungRssClient(RestClient.Builder restClientBuilder) {
        this.restClient = restClientBuilder.build();
    }

    public RssFeedResponse fetchRealEstateNews() {
        return restClient.get()
                .uri(RSS_URL)
                .retrieve()
                .body(RssFeedResponse.class);
    }
}
