-- =====================================================
-- 사용자 관심사 테이블 DDL
-- =====================================================

-- 공통 관심사
CREATE TABLE IF NOT EXISTS user_interest (
    interest_id   BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id       BIGINT NOT NULL,
    category      VARCHAR(30) NOT NULL,
    is_active     BOOLEAN DEFAULT TRUE,
    created_at    TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at    TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(id),
    UNIQUE KEY uk_user_category (user_id, category)
);

-- 스포츠 관심 팀
CREATE TABLE IF NOT EXISTS user_interest_sports (
    id            BIGINT AUTO_INCREMENT PRIMARY KEY,
    interest_id   BIGINT NOT NULL,
    team_id       VARCHAR(30) NOT NULL,
    league_id     VARCHAR(20) NOT NULL,
    score_noti    BOOLEAN DEFAULT TRUE,
    news_noti     BOOLEAN DEFAULT TRUE,
    created_at    TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (interest_id) REFERENCES user_interest(interest_id),
    UNIQUE KEY uk_interest_team (interest_id, team_id)
);

-- 금융 관심 자산
CREATE TABLE IF NOT EXISTS user_interest_finance (
    id            BIGINT AUTO_INCREMENT PRIMARY KEY,
    interest_id   BIGINT NOT NULL,
    asset_type    VARCHAR(20) NOT NULL,
    asset_code    VARCHAR(30) NOT NULL,
    target_price  DECIMAL(18,4),
    alert_enabled BOOLEAN DEFAULT FALSE,
    created_at    TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (interest_id) REFERENCES user_interest(interest_id),
    UNIQUE KEY uk_interest_asset (interest_id, asset_code)
);
