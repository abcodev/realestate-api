-- =============================================================
-- scrap_config 테이블 DDL + 초기 데이터
-- =============================================================

CREATE TABLE IF NOT EXISTS scrap_config (
    id              BIGSERIAL       PRIMARY KEY,
    type            VARCHAR(20)     NOT NULL,           -- SPORT, FINANCE, NEWS
    scrape_method   VARCHAR(10)     NOT NULL,           -- HTML, API
    team_id         VARCHAR(50)     NOT NULL,           -- teams.team_id FK 참조용
    site_name       VARCHAR(100)    NOT NULL,           -- 로그/디버깅용 사이트명
    host_pattern    VARCHAR(200)    NOT NULL,           -- URL 매칭 패턴
    target_url      VARCHAR(500)    NOT NULL,           -- 스크래핑 대상 URL

    -- HTML 파싱 전용 (scrape_method = 'HTML')
    row_selector    VARCHAR(200),                       -- 게시글 행 CSS 셀렉터
    title_selector  VARCHAR(200),                       -- 제목 CSS 셀렉터
    date_selector   VARCHAR(200),                       -- 날짜 CSS 셀렉터

    -- JSON API 전용 (scrape_method = 'API')
    data_path       VARCHAR(200),                       -- JSON 데이터 경로 (콤마 구분)
    title_field     VARCHAR(100),                       -- 제목 JSON 필드명
    date_field      VARCHAR(100),                       -- 날짜 JSON 필드명

    created_at      TIMESTAMP       NOT NULL DEFAULT NOW(),
    updated_at      TIMESTAMP       NOT NULL DEFAULT NOW()
);

-- =============================================================
-- KBO 10개 구단 초기 데이터
-- =============================================================

-- ── HTML 파싱 구단 ──────────────────────────────────────────

INSERT INTO scrap_config (type, scrape_method, team_id, site_name, host_pattern, target_url, row_selector, title_selector, date_selector)
VALUES ('SPORT', 'HTML', 'kbo-samsung', '삼성 라이온즈', 'samsunglions.com',
        'https://www.samsunglions.com/intro/intro02.asp',
        'div.mBoard2 table tbody tr', 'td.tit a', 'td.dat');

INSERT INTO scrap_config (type, scrape_method, team_id, site_name, host_pattern, target_url, row_selector, title_selector, date_selector)
VALUES ('SPORT', 'HTML', 'kbo-lotte', '롯데 자이언츠', 'giantsclub.com',
        'https://www.giantsclub.com/html/index.asp?pcode=783',
        'ul.news-list > li', '.news-list-cont strong', '.news-list-date');

INSERT INTO scrap_config (type, scrape_method, team_id, site_name, host_pattern, target_url, row_selector, title_selector, date_selector)
VALUES ('SPORT', 'HTML', 'kbo-nc', 'NC 다이노스', 'ncdinos.com',
        'https://www.ncdinos.com/dinos/news.do',
        '.board ul li', 'a.title', 'span.date');

INSERT INTO scrap_config (type, scrape_method, team_id, site_name, host_pattern, target_url, row_selector, title_selector, date_selector)
VALUES ('SPORT', 'HTML', 'kbo-lg', 'LG 트윈스', 'lgtwins.com',
        'https://www.lgtwins.com/twins/feed/news',
        '.news_list li', 'span.title', 'span.date');

INSERT INTO scrap_config (type, scrape_method, team_id, site_name, host_pattern, target_url, row_selector, title_selector, date_selector)
VALUES ('SPORT', 'HTML', 'kbo-ssg', 'SSG 랜더스', 'ssglanders.com',
        'https://www.ssglanders.com/media/news',
        'div[style*=''padding: 20px'']', 'h4.text-dotdotdot', 'div[style*=''margin-top: 30px'']');

INSERT INTO scrap_config (type, scrape_method, team_id, site_name, host_pattern, target_url, row_selector, title_selector, date_selector)
VALUES ('SPORT', 'HTML', 'kbo-kiwoom', '키움 히어로즈', 'heroesbaseball.co.kr',
        'https://heroesbaseball.co.kr/story/heroesNews/list.do',
        '.headNotice ul li', 'a', 'span');

-- ── JSON API 구단 ───────────────────────────────────────────

INSERT INTO scrap_config (type, scrape_method, team_id, site_name, host_pattern, target_url, data_path, title_field, date_field)
VALUES ('SPORT', 'API', 'kbo-hanwha', '한화 이글스', 'hanwhaeagles.co.kr',
        'https://www.hanwhaeagles.co.kr/FA/CN/PCFACN01.do',
        'result,data', 'TITLE', 'PUB_DATE');

INSERT INTO scrap_config (type, scrape_method, team_id, site_name, host_pattern, target_url, data_path, title_field, date_field)
VALUES ('SPORT', 'API', 'kbo-doosan', '두산 베어스', 'doosanbears.com',
        'https://www.doosanbears.com/doosan/v1/web/doorun/team-news?page=0&size=11',
        'content', 'title', 'showDate');

INSERT INTO scrap_config (type, scrape_method, team_id, site_name, host_pattern, target_url, data_path, title_field, date_field)
VALUES ('SPORT', 'API', 'kbo-kia', 'KIA 타이거즈', 'tigers.co.kr',
        'https://tigers.co.kr/v1/article/getArticleList?article.boardCode=press_release&search.pos=0&search.max=10',
        'data,list', 'artcTitle', 'regDttm');

INSERT INTO scrap_config (type, scrape_method, team_id, site_name, host_pattern, target_url, data_path, title_field, date_field)
VALUES ('SPORT', 'API', 'kbo-kt', 'KT 위즈', 'ktwiz.co.kr',
        'https://www.ktwiz.co.kr/api/v2/article/listByCategory?article.boardCode=001&search.max=10&search.sort=100&search.page=1',
        'data,list', 'artcTitle', 'regDttm');

-- =============================================================
-- NBA 30개 팀 (ESPN Public API)
-- =============================================================

INSERT INTO scrap_config (type, scrape_method, team_id, site_name, host_pattern, target_url, data_path, title_field, date_field) VALUES
('SPORT', 'API', 'nba-atl', '애틀랜타 호크스', 'espn.com/nba/atl', 'https://site.api.espn.com/apis/site/v2/sports/basketball/nba/news?team=1', 'articles', 'headline', 'published');
INSERT INTO scrap_config (type, scrape_method, team_id, site_name, host_pattern, target_url, data_path, title_field, date_field) VALUES
('SPORT', 'API', 'nba-bos', '보스턴 셀틱스', 'espn.com/nba/bos', 'https://site.api.espn.com/apis/site/v2/sports/basketball/nba/news?team=2', 'articles', 'headline', 'published');
INSERT INTO scrap_config (type, scrape_method, team_id, site_name, host_pattern, target_url, data_path, title_field, date_field) VALUES
('SPORT', 'API', 'nba-bkn', '브루클린 네츠', 'espn.com/nba/bkn', 'https://site.api.espn.com/apis/site/v2/sports/basketball/nba/news?team=17', 'articles', 'headline', 'published');
INSERT INTO scrap_config (type, scrape_method, team_id, site_name, host_pattern, target_url, data_path, title_field, date_field) VALUES
('SPORT', 'API', 'nba-cha', '샬럿 호네츠', 'espn.com/nba/cha', 'https://site.api.espn.com/apis/site/v2/sports/basketball/nba/news?team=30', 'articles', 'headline', 'published');
INSERT INTO scrap_config (type, scrape_method, team_id, site_name, host_pattern, target_url, data_path, title_field, date_field) VALUES
('SPORT', 'API', 'nba-chi', '시카고 불스', 'espn.com/nba/chi', 'https://site.api.espn.com/apis/site/v2/sports/basketball/nba/news?team=4', 'articles', 'headline', 'published');
INSERT INTO scrap_config (type, scrape_method, team_id, site_name, host_pattern, target_url, data_path, title_field, date_field) VALUES
('SPORT', 'API', 'nba-cle', '클리블랜드 캐벌리어스', 'espn.com/nba/cle', 'https://site.api.espn.com/apis/site/v2/sports/basketball/nba/news?team=5', 'articles', 'headline', 'published');
INSERT INTO scrap_config (type, scrape_method, team_id, site_name, host_pattern, target_url, data_path, title_field, date_field) VALUES
('SPORT', 'API', 'nba-dal', '댈러스 매버릭스', 'espn.com/nba/dal', 'https://site.api.espn.com/apis/site/v2/sports/basketball/nba/news?team=6', 'articles', 'headline', 'published');
INSERT INTO scrap_config (type, scrape_method, team_id, site_name, host_pattern, target_url, data_path, title_field, date_field) VALUES
('SPORT', 'API', 'nba-den', '덴버 너기츠', 'espn.com/nba/den', 'https://site.api.espn.com/apis/site/v2/sports/basketball/nba/news?team=7', 'articles', 'headline', 'published');
INSERT INTO scrap_config (type, scrape_method, team_id, site_name, host_pattern, target_url, data_path, title_field, date_field) VALUES
('SPORT', 'API', 'nba-det', '디트로이트 피스톤즈', 'espn.com/nba/det', 'https://site.api.espn.com/apis/site/v2/sports/basketball/nba/news?team=8', 'articles', 'headline', 'published');
INSERT INTO scrap_config (type, scrape_method, team_id, site_name, host_pattern, target_url, data_path, title_field, date_field) VALUES
('SPORT', 'API', 'nba-gs', '골든스테이트 워리어스', 'espn.com/nba/gs', 'https://site.api.espn.com/apis/site/v2/sports/basketball/nba/news?team=9', 'articles', 'headline', 'published');
INSERT INTO scrap_config (type, scrape_method, team_id, site_name, host_pattern, target_url, data_path, title_field, date_field) VALUES
('SPORT', 'API', 'nba-hou', '휴스턴 로켓츠', 'espn.com/nba/hou', 'https://site.api.espn.com/apis/site/v2/sports/basketball/nba/news?team=10', 'articles', 'headline', 'published');
INSERT INTO scrap_config (type, scrape_method, team_id, site_name, host_pattern, target_url, data_path, title_field, date_field) VALUES
('SPORT', 'API', 'nba-ind', '인디애나 페이서스', 'espn.com/nba/ind', 'https://site.api.espn.com/apis/site/v2/sports/basketball/nba/news?team=11', 'articles', 'headline', 'published');
INSERT INTO scrap_config (type, scrape_method, team_id, site_name, host_pattern, target_url, data_path, title_field, date_field) VALUES
('SPORT', 'API', 'nba-lac', 'LA 클리퍼스', 'espn.com/nba/lac', 'https://site.api.espn.com/apis/site/v2/sports/basketball/nba/news?team=12', 'articles', 'headline', 'published');
INSERT INTO scrap_config (type, scrape_method, team_id, site_name, host_pattern, target_url, data_path, title_field, date_field) VALUES
('SPORT', 'API', 'nba-lal', 'LA 레이커스', 'espn.com/nba/lal', 'https://site.api.espn.com/apis/site/v2/sports/basketball/nba/news?team=13', 'articles', 'headline', 'published');
INSERT INTO scrap_config (type, scrape_method, team_id, site_name, host_pattern, target_url, data_path, title_field, date_field) VALUES
('SPORT', 'API', 'nba-mem', '멤피스 그리즐리스', 'espn.com/nba/mem', 'https://site.api.espn.com/apis/site/v2/sports/basketball/nba/news?team=29', 'articles', 'headline', 'published');
INSERT INTO scrap_config (type, scrape_method, team_id, site_name, host_pattern, target_url, data_path, title_field, date_field) VALUES
('SPORT', 'API', 'nba-mia', '마이애미 히트', 'espn.com/nba/mia', 'https://site.api.espn.com/apis/site/v2/sports/basketball/nba/news?team=14', 'articles', 'headline', 'published');
INSERT INTO scrap_config (type, scrape_method, team_id, site_name, host_pattern, target_url, data_path, title_field, date_field) VALUES
('SPORT', 'API', 'nba-mil', '밀워키 벅스', 'espn.com/nba/mil', 'https://site.api.espn.com/apis/site/v2/sports/basketball/nba/news?team=15', 'articles', 'headline', 'published');
INSERT INTO scrap_config (type, scrape_method, team_id, site_name, host_pattern, target_url, data_path, title_field, date_field) VALUES
('SPORT', 'API', 'nba-min', '미네소타 팀버울브스', 'espn.com/nba/min', 'https://site.api.espn.com/apis/site/v2/sports/basketball/nba/news?team=16', 'articles', 'headline', 'published');
INSERT INTO scrap_config (type, scrape_method, team_id, site_name, host_pattern, target_url, data_path, title_field, date_field) VALUES
('SPORT', 'API', 'nba-no', '뉴올리언스 펠리컨스', 'espn.com/nba/no', 'https://site.api.espn.com/apis/site/v2/sports/basketball/nba/news?team=3', 'articles', 'headline', 'published');
INSERT INTO scrap_config (type, scrape_method, team_id, site_name, host_pattern, target_url, data_path, title_field, date_field) VALUES
('SPORT', 'API', 'nba-ny', '뉴욕 닉스', 'espn.com/nba/ny', 'https://site.api.espn.com/apis/site/v2/sports/basketball/nba/news?team=18', 'articles', 'headline', 'published');
INSERT INTO scrap_config (type, scrape_method, team_id, site_name, host_pattern, target_url, data_path, title_field, date_field) VALUES
('SPORT', 'API', 'nba-okc', '오클라호마시티 썬더', 'espn.com/nba/okc', 'https://site.api.espn.com/apis/site/v2/sports/basketball/nba/news?team=25', 'articles', 'headline', 'published');
INSERT INTO scrap_config (type, scrape_method, team_id, site_name, host_pattern, target_url, data_path, title_field, date_field) VALUES
('SPORT', 'API', 'nba-orl', '올랜도 매직', 'espn.com/nba/orl', 'https://site.api.espn.com/apis/site/v2/sports/basketball/nba/news?team=19', 'articles', 'headline', 'published');
INSERT INTO scrap_config (type, scrape_method, team_id, site_name, host_pattern, target_url, data_path, title_field, date_field) VALUES
('SPORT', 'API', 'nba-phi', '필라델피아 세븐티식서스', 'espn.com/nba/phi', 'https://site.api.espn.com/apis/site/v2/sports/basketball/nba/news?team=20', 'articles', 'headline', 'published');
INSERT INTO scrap_config (type, scrape_method, team_id, site_name, host_pattern, target_url, data_path, title_field, date_field) VALUES
('SPORT', 'API', 'nba-phx', '피닉스 선즈', 'espn.com/nba/phx', 'https://site.api.espn.com/apis/site/v2/sports/basketball/nba/news?team=21', 'articles', 'headline', 'published');
INSERT INTO scrap_config (type, scrape_method, team_id, site_name, host_pattern, target_url, data_path, title_field, date_field) VALUES
('SPORT', 'API', 'nba-por', '포틀랜드 트레일블레이저스', 'espn.com/nba/por', 'https://site.api.espn.com/apis/site/v2/sports/basketball/nba/news?team=22', 'articles', 'headline', 'published');
INSERT INTO scrap_config (type, scrape_method, team_id, site_name, host_pattern, target_url, data_path, title_field, date_field) VALUES
('SPORT', 'API', 'nba-sac', '새크라멘토 킹스', 'espn.com/nba/sac', 'https://site.api.espn.com/apis/site/v2/sports/basketball/nba/news?team=23', 'articles', 'headline', 'published');
INSERT INTO scrap_config (type, scrape_method, team_id, site_name, host_pattern, target_url, data_path, title_field, date_field) VALUES
('SPORT', 'API', 'nba-sa', '샌안토니오 스퍼스', 'espn.com/nba/sa', 'https://site.api.espn.com/apis/site/v2/sports/basketball/nba/news?team=24', 'articles', 'headline', 'published');
INSERT INTO scrap_config (type, scrape_method, team_id, site_name, host_pattern, target_url, data_path, title_field, date_field) VALUES
('SPORT', 'API', 'nba-tor', '토론토 랩터스', 'espn.com/nba/tor', 'https://site.api.espn.com/apis/site/v2/sports/basketball/nba/news?team=28', 'articles', 'headline', 'published');
INSERT INTO scrap_config (type, scrape_method, team_id, site_name, host_pattern, target_url, data_path, title_field, date_field) VALUES
('SPORT', 'API', 'nba-utah', '유타 재즈', 'espn.com/nba/utah', 'https://site.api.espn.com/apis/site/v2/sports/basketball/nba/news?team=26', 'articles', 'headline', 'published');
INSERT INTO scrap_config (type, scrape_method, team_id, site_name, host_pattern, target_url, data_path, title_field, date_field) VALUES
('SPORT', 'API', 'nba-wsh', '워싱턴 위저즈', 'espn.com/nba/wsh', 'https://site.api.espn.com/apis/site/v2/sports/basketball/nba/news?team=27', 'articles', 'headline', 'published');
