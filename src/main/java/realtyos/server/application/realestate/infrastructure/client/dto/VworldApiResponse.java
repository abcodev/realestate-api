package realtyos.server.application.realestate.infrastructure.client.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import java.util.List;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class VworldApiResponse {

    private Response response;

    @Getter
    @Setter
    @ToString
    @NoArgsConstructor
    public static class Response {
        private String status;
        private Page page;
        private Result result;
    }

    @Getter
    @Setter
    @ToString
    @NoArgsConstructor
    public static class Page {
        private String total;
        private String current;
        private String size;
    }

    @Getter
    @Setter
    @ToString
    @NoArgsConstructor
    public static class Result {
        private FeatureCollection featureCollection;
    }

    @Getter
    @Setter
    @ToString
    @NoArgsConstructor
    public static class FeatureCollection {
        private List<Feature> features;
    }

    @Getter
    @Setter
    @ToString
    @NoArgsConstructor
    public static class Feature {
        private Properties properties;
    }

    @Getter
    @Setter
    @ToString
    @NoArgsConstructor
    public static class Properties {
        private String sig_cd;
        private String full_nm;
        private String sig_kor_nm;
        private String sig_eng_nm;
    }
}
