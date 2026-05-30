-- =============================================================
-- NBA 리그 + 30개 팀 초기 데이터
-- =============================================================

-- 리그 등록
INSERT INTO leagues (league_id, name, korean_name, sport_type, country, logo_url, founded_year, api_provider_id)
VALUES ('nba', 'National Basketball Association', 'NBA', 'basketball', 'USA',
        'https://a.espncdn.com/i/teamlogos/leagues/500/nba.png', 1946, '46')
ON CONFLICT (league_id) DO NOTHING;

-- 30개 팀 등록
INSERT INTO teams (team_id, league_id, name, korean_name, short_name, color_hex, secondary_color_hex, logo_url, stadium_name, website_url, api_provider_id, created_at, updated_at) VALUES
('nba-atl', 'nba', 'Atlanta Hawks', '애틀랜타 호크스', 'ATL', '#C8102E', '#FDB927', 'https://a.espncdn.com/i/teamlogos/nba/500/atl.png', 'State Farm Arena', 'https://www.nba.com/hawks', '1', NOW(), NOW()),
('nba-bos', 'nba', 'Boston Celtics', '보스턴 셀틱스', 'BOS', '#008348', '#FFFFFF', 'https://a.espncdn.com/i/teamlogos/nba/500/bos.png', 'TD Garden', 'https://www.nba.com/celtics', '2', NOW(), NOW()),
('nba-bkn', 'nba', 'Brooklyn Nets', '브루클린 네츠', 'BKN', '#000000', '#FFFFFF', 'https://a.espncdn.com/i/teamlogos/nba/500/bkn.png', 'Barclays Center', 'https://www.nba.com/nets', '17', NOW(), NOW()),
('nba-cha', 'nba', 'Charlotte Hornets', '샬럿 호네츠', 'CHA', '#008CA8', '#1D1060', 'https://a.espncdn.com/i/teamlogos/nba/500/cha.png', 'Spectrum Center', 'https://www.nba.com/hornets', '30', NOW(), NOW()),
('nba-chi', 'nba', 'Chicago Bulls', '시카고 불스', 'CHI', '#CE1141', '#000000', 'https://a.espncdn.com/i/teamlogos/nba/500/chi.png', 'United Center', 'https://www.nba.com/bulls', '4', NOW(), NOW()),
('nba-cle', 'nba', 'Cleveland Cavaliers', '클리블랜드 캐벌리어스', 'CLE', '#860038', '#BC945C', 'https://a.espncdn.com/i/teamlogos/nba/500/cle.png', 'Rocket Mortgage FieldHouse', 'https://www.nba.com/cavaliers', '5', NOW(), NOW()),
('nba-dal', 'nba', 'Dallas Mavericks', '댈러스 매버릭스', 'DAL', '#0064B1', '#BBC4CA', 'https://a.espncdn.com/i/teamlogos/nba/500/dal.png', 'American Airlines Center', 'https://www.nba.com/mavericks', '6', NOW(), NOW()),
('nba-den', 'nba', 'Denver Nuggets', '덴버 너기츠', 'DEN', '#0E2240', '#FEC524', 'https://a.espncdn.com/i/teamlogos/nba/500/den.png', 'Ball Arena', 'https://www.nba.com/nuggets', '7', NOW(), NOW()),
('nba-det', 'nba', 'Detroit Pistons', '디트로이트 피스톤즈', 'DET', '#1D428A', '#C8102E', 'https://a.espncdn.com/i/teamlogos/nba/500/det.png', 'Little Caesars Arena', 'https://www.nba.com/pistons', '8', NOW(), NOW()),
('nba-gs',  'nba', 'Golden State Warriors', '골든스테이트 워리어스', 'GS',  '#FDB927', '#1D428A', 'https://a.espncdn.com/i/teamlogos/nba/500/gs.png', 'Chase Center', 'https://www.nba.com/warriors', '9', NOW(), NOW()),
('nba-hou', 'nba', 'Houston Rockets', '휴스턴 로켓츠', 'HOU', '#CE1141', '#000000', 'https://a.espncdn.com/i/teamlogos/nba/500/hou.png', 'Toyota Center', 'https://www.nba.com/rockets', '10', NOW(), NOW()),
('nba-ind', 'nba', 'Indiana Pacers', '인디애나 페이서스', 'IND', '#0C2340', '#FFD520', 'https://a.espncdn.com/i/teamlogos/nba/500/ind.png', 'Gainbridge Fieldhouse', 'https://www.nba.com/pacers', '11', NOW(), NOW()),
('nba-lac', 'nba', 'LA Clippers', 'LA 클리퍼스', 'LAC', '#12173F', '#C8102E', 'https://a.espncdn.com/i/teamlogos/nba/500/lac.png', 'Intuit Dome', 'https://www.nba.com/clippers', '12', NOW(), NOW()),
('nba-lal', 'nba', 'Los Angeles Lakers', 'LA 레이커스', 'LAL', '#552583', '#FDB927', 'https://a.espncdn.com/i/teamlogos/nba/500/lal.png', 'Crypto.com Arena', 'https://www.nba.com/lakers', '13', NOW(), NOW()),
('nba-mem', 'nba', 'Memphis Grizzlies', '멤피스 그리즐리스', 'MEM', '#5D76A9', '#12173F', 'https://a.espncdn.com/i/teamlogos/nba/500/mem.png', 'FedExForum', 'https://www.nba.com/grizzlies', '29', NOW(), NOW()),
('nba-mia', 'nba', 'Miami Heat', '마이애미 히트', 'MIA', '#98002E', '#000000', 'https://a.espncdn.com/i/teamlogos/nba/500/mia.png', 'Kaseya Center', 'https://www.nba.com/heat', '14', NOW(), NOW()),
('nba-mil', 'nba', 'Milwaukee Bucks', '밀워키 벅스', 'MIL', '#00471B', '#EEE1C6', 'https://a.espncdn.com/i/teamlogos/nba/500/mil.png', 'Fiserv Forum', 'https://www.nba.com/bucks', '15', NOW(), NOW()),
('nba-min', 'nba', 'Minnesota Timberwolves', '미네소타 팀버울브스', 'MIN', '#266092', '#79BC43', 'https://a.espncdn.com/i/teamlogos/nba/500/min.png', 'Target Center', 'https://www.nba.com/timberwolves', '16', NOW(), NOW()),
('nba-no',  'nba', 'New Orleans Pelicans', '뉴올리언스 펠리컨스', 'NO',  '#0A2240', '#B4975A', 'https://a.espncdn.com/i/teamlogos/nba/500/no.png', 'Smoothie King Center', 'https://www.nba.com/pelicans', '3', NOW(), NOW()),
('nba-ny',  'nba', 'New York Knicks', '뉴욕 닉스', 'NY',  '#1D428A', '#F58426', 'https://a.espncdn.com/i/teamlogos/nba/500/ny.png', 'Madison Square Garden', 'https://www.nba.com/knicks', '18', NOW(), NOW()),
('nba-okc', 'nba', 'Oklahoma City Thunder', '오클라호마시티 썬더', 'OKC', '#007AC1', '#EF3B24', 'https://a.espncdn.com/i/teamlogos/nba/500/okc.png', 'Paycom Center', 'https://www.nba.com/thunder', '25', NOW(), NOW()),
('nba-orl', 'nba', 'Orlando Magic', '올랜도 매직', 'ORL', '#0150B5', '#9CA0A3', 'https://a.espncdn.com/i/teamlogos/nba/500/orl.png', 'Amway Center', 'https://www.nba.com/magic', '19', NOW(), NOW()),
('nba-phi', 'nba', 'Philadelphia 76ers', '필라델피아 세븐티식서스', 'PHI', '#1D428A', '#E01234', 'https://a.espncdn.com/i/teamlogos/nba/500/phi.png', 'Wells Fargo Center', 'https://www.nba.com/sixers', '20', NOW(), NOW()),
('nba-phx', 'nba', 'Phoenix Suns', '피닉스 선즈', 'PHX', '#29127A', '#E56020', 'https://a.espncdn.com/i/teamlogos/nba/500/phx.png', 'Footprint Center', 'https://www.nba.com/suns', '21', NOW(), NOW()),
('nba-por', 'nba', 'Portland Trail Blazers', '포틀랜드 트레일블레이저스', 'POR', '#E03A3E', '#000000', 'https://a.espncdn.com/i/teamlogos/nba/500/por.png', 'Moda Center', 'https://www.nba.com/blazers', '22', NOW(), NOW()),
('nba-sac', 'nba', 'Sacramento Kings', '새크라멘토 킹스', 'SAC', '#5A2D81', '#6A7A82', 'https://a.espncdn.com/i/teamlogos/nba/500/sac.png', 'Golden 1 Center', 'https://www.nba.com/kings', '23', NOW(), NOW()),
('nba-sa',  'nba', 'San Antonio Spurs', '샌안토니오 스퍼스', 'SA',  '#000000', '#C4CED4', 'https://a.espncdn.com/i/teamlogos/nba/500/sa.png', 'Frost Bank Center', 'https://www.nba.com/spurs', '24', NOW(), NOW()),
('nba-tor', 'nba', 'Toronto Raptors', '토론토 랩터스', 'TOR', '#D91244', '#000000', 'https://a.espncdn.com/i/teamlogos/nba/500/tor.png', 'Scotiabank Arena', 'https://www.nba.com/raptors', '28', NOW(), NOW()),
('nba-utah','nba', 'Utah Jazz', '유타 재즈', 'UTAH', '#4E008E', '#79A3DC', 'https://a.espncdn.com/i/teamlogos/nba/500/utah.png', 'Delta Center', 'https://www.nba.com/jazz', '26', NOW(), NOW()),
('nba-wsh', 'nba', 'Washington Wizards', '워싱턴 위저즈', 'WSH', '#E31837', '#002B5C', 'https://a.espncdn.com/i/teamlogos/nba/500/wsh.png', 'Capital One Arena', 'https://www.nba.com/wizards', '27', NOW(), NOW())
ON CONFLICT (team_id) DO NOTHING;
