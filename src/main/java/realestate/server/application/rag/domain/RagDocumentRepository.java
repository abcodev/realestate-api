package realestate.server.application.rag.domain;

import java.util.List;

public interface RagDocumentRepository {

    int buildDealDocuments(int limit);

    List<RagDocumentForEmbedding> findDocumentsWithoutEmbedding(int limit);

    int saveEmbedding(Long documentId, List<Double> embedding);

    List<RagSearchResult> searchByEmbedding(List<Double> embedding, int topK);
}
