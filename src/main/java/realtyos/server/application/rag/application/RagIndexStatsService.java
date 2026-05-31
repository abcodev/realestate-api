package realtyos.server.application.rag.application;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import realtyos.server.application.rag.domain.EmbeddingModelProfile;
import realtyos.server.application.rag.domain.RagDocumentRepository;
import realtyos.server.application.rag.domain.RagIndexStats;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class RagIndexStatsService {

    private final EmbeddingClientRegistry embeddingClientRegistry;
    private final RagDocumentRepository ragDocumentRepository;

    public RagIndexStats getStats(String provider, String model) {
        EmbeddingModelProfile profile = embeddingClientRegistry.resolveProfile(provider, model);
        return ragDocumentRepository.getIndexStats(profile);
    }
}
