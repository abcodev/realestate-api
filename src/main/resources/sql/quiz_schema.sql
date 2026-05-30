-- =====================================================
-- Quiz System DDL
-- =====================================================

CREATE TABLE IF NOT EXISTS quiz_sets (
    id          BIGSERIAL PRIMARY KEY,
    category    VARCHAR(30)  NOT NULL,
    section     VARCHAR(30)  NOT NULL DEFAULT 'difficulty',
    difficulty  VARCHAR(30)  NOT NULL,
    name        VARCHAR(100) NOT NULL,
    description TEXT,
    sort_order  INT          NOT NULL DEFAULT 0,
    is_premium  BOOLEAN      NOT NULL DEFAULT FALSE,
    is_active   BOOLEAN      NOT NULL DEFAULT TRUE,
    created_at  TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at  TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS quiz_questions (
    id                    BIGSERIAL PRIMARY KEY,
    set_id                BIGINT       NOT NULL REFERENCES quiz_sets(id),
    question_id           VARCHAR(30)  NOT NULL UNIQUE,
    question              TEXT         NOT NULL,
    options               TEXT         NOT NULL,
    correct_option_index  INT          NOT NULL,
    explanation           TEXT,
    sort_order            INT          NOT NULL DEFAULT 0,
    created_at            TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS quiz_user_history (
    id              BIGSERIAL PRIMARY KEY,
    user_id         BIGINT    NOT NULL,
    question_id     BIGINT    NOT NULL REFERENCES quiz_questions(id),
    is_correct      BOOLEAN   NOT NULL,
    selected_index  INT       NOT NULL,
    answered_at     TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE INDEX IF NOT EXISTS idx_quiz_user_history_user ON quiz_user_history(user_id);
CREATE INDEX IF NOT EXISTS idx_quiz_user_history_question ON quiz_user_history(user_id, question_id);

CREATE TABLE IF NOT EXISTS quiz_user_set_progress (
    id               BIGSERIAL PRIMARY KEY,
    user_id          BIGINT    NOT NULL,
    set_id           BIGINT    NOT NULL REFERENCES quiz_sets(id),
    total_questions  INT       NOT NULL DEFAULT 0,
    answered_count   INT       NOT NULL DEFAULT 0,
    correct_count    INT       NOT NULL DEFAULT 0,
    is_completed     BOOLEAN   NOT NULL DEFAULT FALSE,
    last_answered_at TIMESTAMP,
    UNIQUE (user_id, set_id)
);

CREATE TABLE IF NOT EXISTS quiz_user_raffle (
    id          BIGSERIAL PRIMARY KEY,
    user_id     BIGINT      NOT NULL,
    event_type  VARCHAR(30) NOT NULL DEFAULT 'daily_quiz',
    earned_date DATE        NOT NULL,
    created_at  TIMESTAMP   NOT NULL DEFAULT CURRENT_TIMESTAMP,
    UNIQUE (user_id, event_type, earned_date)
);

CREATE TABLE IF NOT EXISTS quiz_raffle_winners (
    id           BIGSERIAL PRIMARY KEY,
    draw_month   VARCHAR(7)   NOT NULL,           -- '2026-04' 형식
    prize_rank   INT          NOT NULL,            -- 1, 2, 3
    user_id      BIGINT       NOT NULL,
    nickname     VARCHAR(50),                      -- 당첨 시점 닉네임 스냅샷
    ticket_count INT          NOT NULL DEFAULT 0,  -- 해당 월 보유 응모권 수
    created_at   TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    UNIQUE (draw_month, prize_rank)
);

INSERT INTO quiz_sets (category, section, difficulty, name, description, sort_order, is_premium) VALUES
('japanese', 'difficulty', 'STARTER',      'Starter Japanese', 'Hiragana, greetings, numbers, simple words', 1, false),
('japanese', 'difficulty', 'BEGINNER',     'Beginner Japanese', 'Basic grammar, verbs, adjectives, daily expressions', 2, false),
('japanese', 'difficulty', 'INTERMEDIATE', 'Intermediate Japanese', 'Complex grammar, business, kanji reading', 3, false),
('japanese', 'difficulty', 'ADVANCED',     'Advanced Japanese', 'JLPT N2/N1, idioms, keigo', 4, true)
ON CONFLICT DO NOTHING;

-- 과학 상식 퀴즈 세트
INSERT INTO quiz_sets (category, section, difficulty, name, description, sort_order, is_premium) VALUES
('science', 'difficulty', 'STARTER',      '왕초보 과학', '일상 속 과학 상식, 기초 자연현상',        1, false),
('science', 'difficulty', 'BEGINNER',     '초급 과학',   '초등~중등 수준 물리, 화학, 생물, 지구과학', 2, false),
('science', 'difficulty', 'INTERMEDIATE', '중급 과학',   '고등 수준 과학 개념, 실험, 과학사',        3, false),
('science', 'difficulty', 'ADVANCED',     '고급 과학',   '대학/전문 수준, 최신 과학, 노벨상 주제',   4, true)
ON CONFLICT DO NOTHING;

-- Seed data files (run in order after this schema):
-- [일본어]
--   1. quiz_seed_starter.sql        (STARTER 100 questions)
--   2. quiz_seed_beginner.sql       (BEGINNER 1-50)
--   3. quiz_seed_beginner_2.sql     (BEGINNER 51-100)
--   4. quiz_seed_intermediate.sql   (INTERMEDIATE 1-50)
--   5. quiz_seed_intermediate_2.sql (INTERMEDIATE 51-100)
--   6. quiz_seed_advanced.sql       (ADVANCED 1-50)
--   7. quiz_seed_advanced_2.sql     (ADVANCED 51-100)
-- [과학]
--   8.  quiz_seed_science_starter.sql        (STARTER 100)
--   9.  quiz_seed_science_beginner.sql       (BEGINNER 1-50)
--   10. quiz_seed_science_beginner_2.sql     (BEGINNER 51-100)
--   11. quiz_seed_science_intermediate.sql   (INTERMEDIATE 1-50)
--   12. quiz_seed_science_intermediate_2.sql (INTERMEDIATE 51-100)
--   13. quiz_seed_science_advanced.sql       (ADVANCED 1-50)
--   14. quiz_seed_science_advanced_2.sql     (ADVANCED 51-100)


INSERT INTO quiz_sets (category, section, difficulty, name, description, sort_order, is_premium) VALUES
('english', 'difficulty', 'STARTER',      'Starter English', 'Basic greetings, alphabet, numbers, simple words', 1, false),
('english', 'difficulty', 'BEGINNER',     'Beginner English', 'Basic grammar, verbs, tenses, daily expressions', 2, false),
('english', 'difficulty', 'INTERMEDIATE', 'Intermediate English', 'Complex grammar, idioms, phrasal verbs', 3, false),
('english', 'difficulty', 'ADVANCED',     'Advanced English', 'Advanced grammar, vocabulary, business English', 4, true)
ON CONFLICT DO NOTHING;

-- English seed data files:
--   1. quiz_seed_en_starter.sql        (STARTER 100 questions)
--   2. quiz_seed_en_beginner.sql       (BEGINNER 1-50)
--   3. quiz_seed_en_beginner_2.sql     (BEGINNER 51-100)
--   4. quiz_seed_en_intermediate.sql   (INTERMEDIATE 1-50)
--   5. quiz_seed_en_intermediate_2.sql (INTERMEDIATE 51-100)
--   6. quiz_seed_en_advanced.sql       (ADVANCED 1-50)
--   7. quiz_seed_en_advanced_2.sql     (ADVANCED 51-100)
