package realestate.server.application.common.ai.prompt;

import realestate.server.application.common.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * AI 프롬프트 템플릿 엔티티.
 * <p>
 * 도메인(entity_type)별, AI 공급자(ai_provider)별 프롬프트를 관리합니다.
 * 버전 관리와 활성화 상태를 통해 프롬프트 이력을 추적할 수 있습니다.
 */
@Entity
@Table(name = "ai_prompt_template", uniqueConstraints = @UniqueConstraint(name = "uq_prompt_entity_provider_version", columnNames = {
        "entity_type", "ai_provider", "version" }))
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Getter
public class AiPromptTemplateJpaEntity extends BaseEntity {

    /** 기본키 (자동 증가) */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /** 도메인 구분 (예: FINANCE, SPORTS, REALESTATE, WEATHER) */
    @Column(name = "entity_type", length = 50, nullable = false)
    private String entityType;

    /** AI 공급자 구분 (예: OPENAI, GEMINI) */
    @Column(name = "ai_provider", length = 20, nullable = false)
    private String aiProvider;

    /** 프롬프트 이름 (관리 식별용) */
    @Column(name = "name", length = 100, nullable = false)
    private String name;

    /** 시스템 프롬프트 — AI의 역할과 행동을 지시하는 프롬프트 */
    @Column(name = "system_prompt", columnDefinition = "TEXT", nullable = false)
    private String systemPrompt;

    /** 유저 프롬프트 템플릿 — {{content}} 같은 변수 치환용 (선택) */
    @Column(name = "user_prompt_template", columnDefinition = "TEXT")
    private String userPromptTemplate;

    /** AI 모델 지정 (예: gpt-4o-mini, gemini-2.0-flash) */
    @Column(name = "model", length = 50)
    private String model;

    /** 프롬프트 버전 번호 */
    @Column(name = "version", nullable = false)
    @Builder.Default
    private Integer version = 1;

    /** 활성화 여부 — entity_type + ai_provider 조합당 active는 1개만 권장 */
    @Convert(disableConversion = true)
    @Column(name = "is_active", columnDefinition = "boolean")
    @Builder.Default
    private Boolean isActive = true;

    /** 프롬프트 설명 */
    @Column(name = "description", length = 500)
    private String description;

    /** 온도 (창의성 조절, 0.0 ~ 2.0) */
    @Column(name = "temperature", precision = 3, scale = 2)
    @Builder.Default
    private BigDecimal temperature = new BigDecimal("0.7");

    /** 최대 응답 토큰 수 */
    @Column(name = "max_tokens")
    @Builder.Default
    private Integer maxTokens = 1024;
}
