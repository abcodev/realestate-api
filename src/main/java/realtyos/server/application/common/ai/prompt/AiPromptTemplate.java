package realtyos.server.application.common.ai.prompt;

import lombok.Builder;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Builder
public record AiPromptTemplate(
                /** 기본키 */
                Long id,
                /** 도메인 구분 (예: FINANCE, SPORTS) */
                String entityType,
                /** AI 공급자 구분 (예: OPENAI, GEMINI) */
                String aiProvider,
                /** 프롬프트 이름 (관리 식별용) */
                String name,
                /** 시스템 프롬프트 — AI의 역할과 행동을 지시 */
                String systemPrompt,
                /** 유저 프롬프트 템플릿 — {{content}} 변수 치환용 */
                String userPromptTemplate,
                /** AI 모델 지정 (예: gpt-4o-mini, gemini-2.0-flash) */
                String model,
                /** 프롬프트 버전 번호 */
                Integer version,
                /** 활성화 여부 */
                Boolean isActive,
                /** 프롬프트 설명 */
                String description,
                /** 온도 (창의성 조절, 0.0 ~ 2.0) */
                BigDecimal temperature,
                /** 최대 응답 토큰 수 */
                Integer maxTokens,
                /** 생성일시 */
                LocalDateTime createdAt,
                /** 수정일시 */
                LocalDateTime updatedAt) {
}
