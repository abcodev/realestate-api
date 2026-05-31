package realestate.server.application.rag.application;

import java.util.List;

public record RagAnswerResponse(
        String answer,
        List<RagAnswerSource> sources
) {
}
