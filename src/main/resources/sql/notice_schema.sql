-- =====================================================
-- App Notice System DDL
-- =====================================================

CREATE TABLE IF NOT EXISTS app_notice (
    id           BIGSERIAL PRIMARY KEY,
    notice_type  VARCHAR(30)  NOT NULL,  -- 'RAFFLE_WINNER', 'URGENT', 'EVENT'
    title        VARCHAR(200) NOT NULL,
    content      TEXT         NOT NULL,
    is_active    BOOLEAN      NOT NULL DEFAULT TRUE,
    start_date   TIMESTAMP,              -- NULL이면 즉시 노출
    end_date     TIMESTAMP,              -- NULL이면 무기한
    created_at   TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE INDEX IF NOT EXISTS idx_app_notice_active ON app_notice(is_active, start_date, end_date);
