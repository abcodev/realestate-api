package realtyos.server.application.common.ai.prompt;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

public interface AiPromptTemplateRepository extends JpaRepository<AiPromptTemplateJpaEntity, Long> {

    /**
     * 특정 도메인 + AI 공급자 조합에서 활성화된 프롬프트를 조회합니다.
     */
    Optional<AiPromptTemplateJpaEntity> findByEntityTypeAndAiProviderAndIsActiveTrue(String entityType,
            String aiProvider);

    /**
     * 특정 도메인에서 활성화된 프롬프트를 조회합니다. (공급자 무관)
     */
    Optional<AiPromptTemplateJpaEntity> findByEntityTypeAndIsActiveTrue(String entityType);

    /**
     * 프롬프트 템플릿의 모델, maxTokens, systemPrompt를 업데이트합니다.
     */
    @Modifying
    @Transactional
    @Query("UPDATE AiPromptTemplateJpaEntity t SET t.model = :model, t.maxTokens = :maxTokens, t.systemPrompt = :systemPrompt WHERE t.id = :id")
    void updateModelAndSettings(@Param("id") Long id, @Param("model") String model,
            @Param("maxTokens") Integer maxTokens, @Param("systemPrompt") String systemPrompt);

    /**
     * entityType + aiProvider로 프롬프트의 모델, maxTokens, systemPrompt, isActive를
     * 업데이트합니다.
     */
    @Modifying
    @Transactional
    @Query("UPDATE AiPromptTemplateJpaEntity t SET t.model = :model, t.maxTokens = :maxTokens, t.systemPrompt = :systemPrompt, t.isActive = true WHERE t.entityType = :entityType AND t.aiProvider = :aiProvider")
    void updateByEntityTypeAndProvider(@Param("entityType") String entityType, @Param("aiProvider") String aiProvider,
            @Param("model") String model, @Param("maxTokens") Integer maxTokens,
            @Param("systemPrompt") String systemPrompt);
}
