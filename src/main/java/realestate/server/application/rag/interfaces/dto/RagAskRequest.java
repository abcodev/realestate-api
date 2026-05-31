package realestate.server.application.rag.interfaces.dto;

public record RagAskRequest(
        String query,
        Integer topK
) {
}
