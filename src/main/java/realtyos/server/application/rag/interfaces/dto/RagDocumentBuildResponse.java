package realtyos.server.application.rag.interfaces.dto;

import realtyos.server.application.rag.domain.RagDocumentBuildResult;

public record RagDocumentBuildResponse(
        int upsertedCount
) {
    public static RagDocumentBuildResponse from(RagDocumentBuildResult result) {
        return new RagDocumentBuildResponse(result.upsertedCount());
    }
}
