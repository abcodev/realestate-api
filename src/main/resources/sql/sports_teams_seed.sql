-- =====================================================
-- 스포츠 리그 & 팀 시드 데이터
-- Flutter 앱의 SportsTeam.allTeams 하드코딩을 대체합니다.
-- =====================================================

-- === 리그 시딩 ===
INSERT INTO leagues (league_id, name, korean_name, sport_type, country, founded_year, api_provider_id)
VALUES
    ('kbo', 'Korea Baseball Organization', 'KBO', 'baseball', 'Korea', 1982, NULL),
    ('epl', 'Premier League', '프리미어리그', 'soccer', 'England', 1992, '23'),
    ('mlb', 'Major League Baseball', 'MLB', 'baseball', 'USA', 1903, '10'),
    ('nba', 'National Basketball Association', 'NBA', 'basketball', 'USA', 1946, '46')
ON CONFLICT (league_id) DO NOTHING;

-- === KBO (10 Teams) ===
INSERT INTO teams (team_id, league_id, name, korean_name, short_name, color_hex, logo_url, api_provider_id)
VALUES
    ('kbo-kia',     'kbo', 'KIA Tigers',     'KIA 타이거즈',  'HT', '#EA0029', 'https://sports-phinf.pstatic.net/team/kbo/default/HT.png', 'HT'),
    ('kbo-samsung', 'kbo', 'Samsung Lions',   '삼성 라이온즈',  'SS', '#074CA1', 'https://sports-phinf.pstatic.net/team/kbo/default/SS.png', 'SS'),
    ('kbo-lg',      'kbo', 'LG Twins',        'LG 트윈스',     'LG', '#C30452', 'https://sports-phinf.pstatic.net/team/kbo/default/LG.png', 'LG'),
    ('kbo-doosan',  'kbo', 'Doosan Bears',    '두산 베어스',    'OB', '#131230', 'https://sports-phinf.pstatic.net/team/kbo/default/OB.png', 'OB'),
    ('kbo-kt',      'kbo', 'KT Wiz',          'KT 위즈',       'KT', '#000000', 'https://sports-phinf.pstatic.net/team/kbo/default/KT.png', 'KT'),
    ('kbo-ssg',     'kbo', 'SSG Landers',     'SSG 랜더스',    'SK', '#CE0E2D', 'https://sports-phinf.pstatic.net/team/kbo/default/SK.png', 'SK'),
    ('kbo-lotte',   'kbo', 'Lotte Giants',    '롯데 자이언츠',  'LT', '#002856', 'https://sports-phinf.pstatic.net/team/kbo/default/LT.png', 'LT'),
    ('kbo-hanwha',  'kbo', 'Hanwha Eagles',   '한화 이글스',    'HH', '#FF6600', 'https://sports-phinf.pstatic.net/team/kbo/default/HH.png', 'HH'),
    ('kbo-nc',      'kbo', 'NC Dinos',        'NC 다이노스',    'NC', '#315288', 'https://sports-phinf.pstatic.net/team/kbo/default/NC.png', 'NC'),
    ('kbo-kiwoom',  'kbo', 'Kiwoom Heroes',   '키움 히어로즈',  'WO', '#820024', 'https://sports-phinf.pstatic.net/team/kbo/default/WO.png', 'WO')
ON CONFLICT (team_id) DO UPDATE SET
    logo_url = EXCLUDED.logo_url,
    api_provider_id = EXCLUDED.api_provider_id;

-- === Premier League (20 Teams) ===
INSERT INTO teams (team_id, league_id, name, korean_name, short_name, color_hex, api_provider_id)
VALUES
    ('epl-mancity',        'epl', 'Man City',          '맨체스터 시티',          'MCI', '#6CABDD', '382'),
    ('epl-arsenal',        'epl', 'Arsenal',           '아스날',                'ARS', '#EF0107', '359'),
    ('epl-liverpool',      'epl', 'Liverpool',         '리버풀',                'LIV', '#C8102E', '364'),
    ('epl-tottenham',      'epl', 'Tottenham',         '토트넘',                'TOT', '#132257', '367'),
    ('epl-manutd',         'epl', 'Man Utd',           '맨체스터 유나이티드',      'MUN', '#DA291C', '360'),
    ('epl-chelsea',        'epl', 'Chelsea',           '첼시',                  'CHE', '#034694', '363'),
    ('epl-newcastle',      'epl', 'Newcastle',         '뉴캐슬',                'NEW', '#241F20', '361'),
    ('epl-villa',          'epl', 'Aston Villa',       '아스톤 빌라',            'AVL', '#95BFE5', '362'),
    ('epl-brighton',       'epl', 'Brighton',          '브라이튼',               'BHA', '#0057B8', '331'),
    ('epl-westham',        'epl', 'West Ham',          '웨스트햄',               'WHU', '#7A263A', '371'),
    ('epl-crystalpalace',  'epl', 'Crystal Palace',    '크리스탈 팰리스',         'CRY', '#1B458F', '384'),
    ('epl-wolves',         'epl', 'Wolves',            '울버햄튼',               'WOL', '#FDB913', '379'),
    ('epl-fulham',         'epl', 'Fulham',            '풀럼',                  'FUL', '#CC0000', '370'),
    ('epl-bournemouth',    'epl', 'Bournemouth',       '본머스',                'BOU', '#DA291C', '349'),
    ('epl-brentford',      'epl', 'Brentford',         '브렌트포드',             'BRE', '#E30613', '402'),
    ('epl-everton',        'epl', 'Everton',           '에버튼',                'EVE', '#003399', '368'),
    ('epl-forest',         'epl', 'Nottm Forest',      '노팅엄 포레스트',         'NFO', '#DD0000', '351'),
    ('epl-leicester',      'epl', 'Leicester',         '레스터 시티',            'LEI', '#0053A0', '375'),
    ('epl-southampton',    'epl', 'Southampton',       '사우샘프턴',             'SOU', '#D71920', '376'),
    ('epl-ipswich',        'epl', 'Ipswich',           '입스위치',               'IPS', '#3A64A3', '373')
ON CONFLICT (team_id) DO UPDATE SET
    api_provider_id = EXCLUDED.api_provider_id;

-- === MLB (30 Teams) ===
INSERT INTO teams (team_id, league_id, name, korean_name, short_name, color_hex, api_provider_id)
VALUES
    -- AL East
    ('mlb-orioles',      'mlb', 'Baltimore Orioles',       '볼티모어 오리올스',         'BAL', '#DF4601', '1'),
    ('mlb-redsox',       'mlb', 'Boston Red Sox',          '보스턴 레드삭스',           'BOS', '#BD3039', '2'),
    ('mlb-yankees',      'mlb', 'NY Yankees',              '뉴욕 양키스',              'NYY', '#003087', '10'),
    ('mlb-rays',         'mlb', 'Tampa Bay Rays',          '탬파베이 레이스',           'TB',  '#092C5C', '30'),
    ('mlb-bluejays',     'mlb', 'Toronto Blue Jays',       '토론토 블루제이스',          'TOR', '#134A8E', '14'),
    -- AL Central
    ('mlb-whitesox',     'mlb', 'Chicago White Sox',       '시카고 화이트삭스',          'CWS', '#27251F', '4'),
    ('mlb-guardians',    'mlb', 'Cleveland Guardians',     '클리블랜드 가디언스',        'CLE', '#E31937', '5'),
    ('mlb-tigers',       'mlb', 'Detroit Tigers',          '디트로이트 타이거스',        'DET', '#0C2340', '6'),
    ('mlb-royals',       'mlb', 'KC Royals',               '캔자스시티 로열스',          'KC',  '#004687', '7'),
    ('mlb-twins',        'mlb', 'Minnesota Twins',         '미네소타 트윈스',           'MIN', '#002B5C', '9'),
    -- AL West
    ('mlb-astros',       'mlb', 'Houston Astros',          '휴스턴 애스트로스',          'HOU', '#EB6E1F', '18'),
    ('mlb-angels',       'mlb', 'LA Angels',               'LA 에인절스',              'LAA', '#BA0021', '3'),
    ('mlb-athletics',    'mlb', 'Oakland Athletics',       '오클랜드 애슬레틱스',        'OAK', '#003831', '11'),
    ('mlb-mariners',     'mlb', 'Seattle Mariners',        '시애틀 매리너스',           'SEA', '#005C5C', '12'),
    ('mlb-rangers',      'mlb', 'Texas Rangers',           '텍사스 레인저스',           'TEX', '#003278', '13'),
    -- NL East
    ('mlb-braves',       'mlb', 'Atlanta Braves',          '애틀랜타 브레이브스',        'ATL', '#13274F', '15'),
    ('mlb-marlins',      'mlb', 'Miami Marlins',           '마이애미 말린스',           'MIA', '#00A3E0', '28'),
    ('mlb-mets',         'mlb', 'NY Mets',                 '뉴욕 메츠',               'NYM', '#FF5910', '21'),
    ('mlb-phillies',     'mlb', 'Philadelphia Phillies',   '필라델피아 필리스',          'PHI', '#E81828', '22'),
    ('mlb-nationals',    'mlb', 'Washington Nationals',    '워싱턴 내셔널스',           'WSH', '#AB0003', '20'),
    -- NL Central
    ('mlb-cubs',         'mlb', 'Chicago Cubs',            '시카고 컵스',              'CHC', '#0E3386', '16'),
    ('mlb-reds',         'mlb', 'Cincinnati Reds',         '신시내티 레즈',             'CIN', '#C6011F', '17'),
    ('mlb-brewers',      'mlb', 'Milwaukee Brewers',       '밀워키 브루어스',           'MIL', '#FFC52F', '8'),
    ('mlb-pirates',      'mlb', 'Pittsburgh Pirates',      '피츠버그 파이리츠',          'PIT', '#FDB827', '23'),
    ('mlb-cardinals',    'mlb', 'St. Louis Cardinals',     '세인트루이스 카디널스',       'STL', '#C41E3A', '24'),
    -- NL West
    ('mlb-diamondbacks', 'mlb', 'Arizona Diamondbacks',    '애리조나 다이아몬드백스',     'AZ',  '#A71930', '29'),
    ('mlb-rockies',      'mlb', 'Colorado Rockies',        '콜로라도 로키스',           'COL', '#33006F', '27'),
    ('mlb-dodgers',      'mlb', 'LA Dodgers',              'LA 다저스',               'LAD', '#005A9C', '19'),
    ('mlb-padres',       'mlb', 'SD Padres',               '샌디에이고 파드리스',        'SD',  '#2F241D', '25'),
    ('mlb-giants',       'mlb', 'SF Giants',               '샌프란시스코 자이언츠',      'SF',  '#FD5A1E', '26')
ON CONFLICT (team_id) DO UPDATE SET
    api_provider_id = EXCLUDED.api_provider_id;

-- === NBA (30 Teams) ===
INSERT INTO teams (team_id, league_id, name, korean_name, short_name, color_hex, api_provider_id)
VALUES
    -- Eastern Conference
    ('nba-celtics',       'nba', 'Boston Celtics',              '보스턴 셀틱스',              'BOS', '#007A33', '2'),
    ('nba-nets',          'nba', 'Brooklyn Nets',               '브루클린 네츠',              'BKN', '#000000', '17'),
    ('nba-knicks',        'nba', 'New York Knicks',             '뉴욕 닉스',                 'NYK', '#006BB6', '18'),
    ('nba-76ers',         'nba', 'Philadelphia 76ers',          '필라델피아 세븐티식서스',      'PHI', '#006BB6', '20'),
    ('nba-raptors',       'nba', 'Toronto Raptors',             '토론토 랩터스',              'TOR', '#CE1141', '28'),
    ('nba-bulls',         'nba', 'Chicago Bulls',               '시카고 불스',                'CHI', '#CE1141', '4'),
    ('nba-cavaliers',     'nba', 'Cleveland Cavaliers',         '클리블랜드 캐벌리어스',       'CLE', '#6F263D', '5'),
    ('nba-pistons',       'nba', 'Detroit Pistons',             '디트로이트 피스톤스',         'DET', '#1D428A', '8'),
    ('nba-pacers',        'nba', 'Indiana Pacers',              '인디애나 페이서스',          'IND', '#002D62', '11'),
    ('nba-bucks',         'nba', 'Milwaukee Bucks',             '밀워키 벅스',               'MIL', '#00471B', '15'),
    ('nba-hawks',         'nba', 'Atlanta Hawks',               '애틀랜타 호크스',            'ATL', '#E03A3E', '1'),
    ('nba-hornets',       'nba', 'Charlotte Hornets',           '샬럿 호네츠',               'CHA', '#00788C', '30'),
    ('nba-heat',          'nba', 'Miami Heat',                  '마이애미 히트',              'MIA', '#98002E', '14'),
    ('nba-magic',         'nba', 'Orlando Magic',               '올랜도 매직',               'ORL', '#0077C0', '19'),
    ('nba-wizards',       'nba', 'Washington Wizards',          '워싱턴 위저즈',              'WAS', '#002B5C', '27'),
    -- Western Conference
    ('nba-nuggets',       'nba', 'Denver Nuggets',              '덴버 너기츠',               'DEN', '#0E2240', '7'),
    ('nba-timberwolves',  'nba', 'Minnesota Timberwolves',      '미네소타 팀버울브스',         'MIN', '#236192', '16'),
    ('nba-thunder',       'nba', 'Oklahoma City Thunder',       '오클라호마시티 썬더',         'OKC', '#007AC1', '25'),
    ('nba-blazers',       'nba', 'Portland Trail Blazers',      '포틀랜드 트레일블레이저스',    'POR', '#E03A3E', '22'),
    ('nba-jazz',          'nba', 'Utah Jazz',                   '유타 재즈',                 'UTA', '#002B5C', '26'),
    ('nba-warriors',      'nba', 'Golden State Warriors',       '골든스테이트 워리어스',       'GSW', '#1D428A', '9'),
    ('nba-clippers',      'nba', 'LA Clippers',                 'LA 클리퍼스',               'LAC', '#1D428A', '12'),
    ('nba-lakers',        'nba', 'LA Lakers',                   'LA 레이커스',               'LAL', '#552583', '13'),
    ('nba-suns',          'nba', 'Phoenix Suns',                '피닉스 선즈',               'PHX', '#1D1160', '21'),
    ('nba-kings',         'nba', 'Sacramento Kings',            '새크라멘토 킹스',            'SAC', '#5A2D81', '23'),
    ('nba-mavericks',     'nba', 'Dallas Mavericks',            '댈러스 매버릭스',            'DAL', '#0053BC', '6'),
    ('nba-rockets',       'nba', 'Houston Rockets',             '휴스턴 로키츠',              'HOU', '#CE1141', '10'),
    ('nba-grizzlies',     'nba', 'Memphis Grizzlies',           '멤피스 그리즐리스',           'MEM', '#5D76A9', '29'),
    ('nba-pelicans',      'nba', 'New Orleans Pelicans',        '뉴올리언스 펠리컨스',         'NOP', '#0C2340', '3'),
    ('nba-spurs',         'nba', 'San Antonio Spurs',           '샌안토니오 스퍼스',          'SAS', '#C4CED4', '24')
ON CONFLICT (team_id) DO UPDATE SET
    api_provider_id = EXCLUDED.api_provider_id;
