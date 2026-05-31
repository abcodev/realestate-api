package realestate.server.application.rag.application;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import realestate.server.application.rag.domain.EmbeddingClient;
import realestate.server.application.rag.domain.RagDocumentForEmbedding;
import realestate.server.application.rag.domain.RagDocumentRepository;
import realestate.server.application.rag.domain.RagEmbeddingBuildResult;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class RagEmbeddingBuildService {

    private final RagDocumentRepository ragDocumentRepository;
    private final EmbeddingClient embeddingClient;

    @Transactional
    public RagEmbeddingBuildResult buildDocumentEmbeddings(int limit) {
        List<RagDocumentForEmbedding> documents = ragDocumentRepository.findDocumentsWithoutEmbedding(limit);
        if (documents.isEmpty()) {
            return new RagEmbeddingBuildResult(0, 0, 0);
        }

        List<String> inputs = documents.stream()
                .map(RagDocumentForEmbedding::content)
                .toList();

        List<List<Double>> embeddings = embeddingClient.embed(inputs);
        if (embeddings.size() != documents.size()) {
            throw new IllegalStateException("Embedding 응답 개수가 요청 문서 개수와 다릅니다.");
        }

        int embeddedCount = 0;
        int failedCount = 0;
        for (int i = 0; i < documents.size(); i++) {
            try {
                embeddedCount += ragDocumentRepository.saveEmbedding(documents.get(i).id(), embeddings.get(i));
            } catch (Exception e) {
                failedCount++;
                log.error("RAG embedding 저장 실패 - documentId: {}", documents.get(i).id(), e);
            }
        }

        int skippedCount = documents.size() - embeddedCount - failedCount;
        log.info("RAG embedding build completed - embedded: {}, skipped: {}, failed: {}, limit: {}",
                embeddedCount, skippedCount, failedCount, limit);
        return new RagEmbeddingBuildResult(embeddedCount, skippedCount, failedCount);
    }
}
