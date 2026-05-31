package realestate.server.application.rag.domain;

import java.util.List;

public interface EmbeddingClient {

    List<List<Double>> embed(List<String> inputs);
}
