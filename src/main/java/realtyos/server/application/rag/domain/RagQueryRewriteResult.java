package realtyos.server.application.rag.domain;

public record RagQueryRewriteResult(
        String rewrittenQuery,
        RagSearchCondition condition
) {
}
