-- ============================================================
-- AI 프롬프트 템플릿 관리 테이블
-- ============================================================

CREATE TABLE ai_prompt_template (
    id                    BIGSERIAL PRIMARY KEY,

    -- 도메인 구분 (FINANCE, SPORTS, REALESTATE, WEATHER 등)
    entity_type           VARCHAR(50)  NOT NULL,

    -- AI 공급자 구분 (OPENAI, GEMINI)
    ai_provider           VARCHAR(20)  NOT NULL,

    -- 프롬프트 이름 (관리 식별용)
    name                  VARCHAR(100) NOT NULL,

    -- 시스템 프롬프트 (AI의 역할과 행동을 지시하는 프롬프트)
    system_prompt         TEXT         NOT NULL,

    -- 유저 프롬프트 템플릿 ({{content}} 같은 변수 치환용, 선택)
    user_prompt_template  TEXT,

    -- AI 모델 지정 (gpt-4o-mini, gemini-2.5-flash 등)
    model                 VARCHAR(50),

    -- 프롬프트 버전 번호
    version               INTEGER      NOT NULL DEFAULT 1,

    -- 활성화 여부 (entity_type + ai_provider 조합당 1개만 active 권장)
    is_active             BOOLEAN      NOT NULL DEFAULT TRUE,

    -- 프롬프트 설명
    description           VARCHAR(500),

    -- 온도 (창의성 조절, 0.0 ~ 2.0)
    temperature           DECIMAL(3,2) DEFAULT 0.7,

    -- 최대 응답 토큰 수
    max_tokens            INTEGER      DEFAULT 1024,

    -- 생성/수정 일시
    created_at            TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP NOT NULL,
    updated_at            TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP NOT NULL,

    -- 같은 도메인 + 공급자에서 버전 유니크 제약
    CONSTRAINT uq_prompt_entity_provider_version UNIQUE (entity_type, ai_provider, version)
);

-- 활성 프롬프트 빠른 조회용 부분 인덱스
CREATE INDEX idx_prompt_active
    ON ai_prompt_template (entity_type, ai_provider, is_active)
    WHERE is_active = TRUE;

COMMENT ON TABLE  ai_prompt_template                         IS 'AI 프롬프트 템플릿 관리 테이블';
COMMENT ON COLUMN ai_prompt_template.entity_type             IS '도메인 구분 (FINANCE, SPORTS, REALESTATE, WEATHER 등)';
COMMENT ON COLUMN ai_prompt_template.ai_provider             IS 'AI 공급자 구분 (OPENAI, GEMINI)';
COMMENT ON COLUMN ai_prompt_template.name                    IS '프롬프트 이름 (관리 식별용)';
COMMENT ON COLUMN ai_prompt_template.system_prompt           IS '시스템 프롬프트 — AI의 역할과 행동을 지시';
COMMENT ON COLUMN ai_prompt_template.user_prompt_template    IS '유저 프롬프트 템플릿 — {{content}} 변수 치환용';
COMMENT ON COLUMN ai_prompt_template.model                   IS 'AI 모델 지정 (gpt-4o-mini, gemini-2.5-flash 등)';
COMMENT ON COLUMN ai_prompt_template.version                 IS '프롬프트 버전 번호';
COMMENT ON COLUMN ai_prompt_template.is_active               IS '활성화 여부 (entity_type + ai_provider 조합당 1개만 active 권장)';
COMMENT ON COLUMN ai_prompt_template.description             IS '프롬프트 설명';
COMMENT ON COLUMN ai_prompt_template.temperature             IS '온도 (창의성 조절, 0.0 ~ 2.0)';
COMMENT ON COLUMN ai_prompt_template.max_tokens              IS '최대 응답 토큰 수';

-- ============================================================
-- 예시 데이터
-- ============================================================

INSERT INTO ai_prompt_template (entity_type, ai_provider, name, system_prompt, user_prompt_template, model, version, is_active, description, temperature, max_tokens, created_at, updated_at)
VALUES
    ('FINANCE', 'OPENAI', '금융 뉴스 요약', '당신은 금융 전문 애널리스트입니다. 사용자가 제공하는 금융 시장 데이터를 분석하여 반드시 JSON 형식으로만 응답해주세요. JSON 외의 텍스트는 절대 포함하지 마세요. 응답 형식: {"oneLineBrief": "피드에 표시할 한 줄 요약 (30자 이내)", "summary": "시장 동향, 주요 변동 요인, 투자자 주의사항을 포함한 3~5문장 분석. 내용이 길어질 경우 문맥에 맞게 개행 문자(\\n\\n)를 삽입해 읽기 좋게 단락을 나눠주세요."}', '다음 금융 뉴스를 요약해주세요:\n\n{{content}}', 'gpt-4o-mini', true, 1, '금융 뉴스 요약용 OpenAI 프롬프트', 0.5, 1024, NOW(), NOW()),

    ('FINANCE', 'GEMINI', '금융 시장 분석', '당신은 금융 전문 애널리스트입니다. 사용자가 제공하는 금융 시장 데이터를 분석하여 반드시 JSON 형식으로만 응답해주세요. 현재 날짜는 ${sysdate}입니다. 금일의 주요 뉴스와 여러가지 시장 상황을 결합하여 요약해주세요. JSON 외의 텍스트는 절대 포함하지 마세요. 응답 형식: {"oneLineBrief": "피드에 표시할 한 줄 요약 (30자 이내)", "summary": "시장 동향, 주요 변동 요인, 투자자 주의사항을 포함한 3~5문장 분석. 내용이 길어질 경우 문맥에 맞게 개행 문자(\\n\\n)를 삽입해 읽기 좋게 단락을 나눠주세요."}', '다음 금융 뉴스를 요약해주세요:\n\n{{content}}', 'gemini-2.5-flash', true, 1, '금융 시장 분석용 Gemini 프롬프트 (JSON 응답)', 0.5, 8192, NOW(), NOW()),

    ('SPORTS', 'OPENAI', '스포츠 뉴스 요약', '당신은 스포츠 전문 기자입니다. 경기 결과와 주요 이벤트를 객관적이고 흥미롭게 요약해주세요.', '다음 스포츠 뉴스를 요약해주세요:\n\n{{content}}', 'gpt-4o-mini', true, 1, '스포츠 뉴스 요약용 OpenAI 프롬프트', 0.7, 1024, NOW(), NOW()),

    ('REALESTATE', 'GEMINI', '부동산 뉴스 요약', '당신은 부동산 전문가입니다.
사용자가 전달하는 기사 제목들 중에서 ''분양'', ''청약'', 특정 단지 홍보, 건설사 마케팅 등 광고성 성격의 기사는 철저히 제외하십시오.

대신 아래 내용만 선별하십시오:
- 분양가 상한제, 대출 규제, 세금 등 부동산 정책
- 매매/전세/월세 가격 변화 및 거래량 흐름
- 금리, 경기, 공급 등 거시 경제 동향

선별된 내용을 바탕으로 전체 시장 흐름을 파악할 수 있는 브리핑을 작성하십시오.

[문체 및 언어 규칙]
- 모든 문장은 반드시 한국어로 작성해야 합니다.
- 모든 문장은 반드시 "~입니다.", "~습니다." 형태의 정중한 서술형으로 끝나야 합니다.
- 반말, 구어체, 영어 혼용을 금지합니다.

[출력 형식]
아래 JSON 형식을 반드시 준수하십시오 (마크다운 금지, 순수 JSON만 출력):

{
  "oneLineBrief": "오늘의 부동산 주요 동향을 한 줄로 요약한 문장입니다.",
  "summary": "선별된 내용을 바탕으로 작성한 1~2줄 분량의 상세 브리핑입니다."
}', '다음 부동산 뉴스를 분석하고 요약해주세요:\n\n{{content}}', 'gemini-2.5-flash', true, 1, '부동산 뉴스 요약용 Gemini 프롬프트', 0.5, 2048, NOW(), NOW());
