package realestate.server.application.rag.domain;

import java.util.List;

public record RagAnswer(
        String answer,
        List<RagAnswerSource> sources
) {
}
