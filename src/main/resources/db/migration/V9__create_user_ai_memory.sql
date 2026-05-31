CREATE TABLE user_ai_memory (
    id BIGSERIAL PRIMARY KEY,
    user_id BIGINT NOT NULL UNIQUE,
    preferred_region VARCHAR(100),
    recent_region VARCHAR(100),
    min_price BIGINT,
    max_price BIGINT,
    query_count BIGINT NOT NULL DEFAULT 0,
    last_query TEXT,
    created_at TIMESTAMP NOT NULL DEFAULT now(),
    updated_at TIMESTAMP NOT NULL DEFAULT now()
);

CREATE INDEX idx_user_ai_memory_user_id
    ON user_ai_memory (user_id);
