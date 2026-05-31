package realestate.server.application.rag.interfaces.dto;

import realestate.server.application.rag.domain.RagAnswer;

import java.util.List;

public record RagAnswerResponse(
        String answer,
        List<RagAnswerSourceResponse> sources
) {
    public static RagAnswerResponse from(RagAnswer answer) {
        return new RagAnswerResponse(
                answer.answer(),
                answer.sources().stream()
                        .map(RagAnswerSourceResponse::from)
                        .toList()
        );
    }
}
