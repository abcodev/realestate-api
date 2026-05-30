-- MLB 리그 추가
INSERT INTO leagues (league_id, name, korean_name, sport_type, country, logo_url, founded_year, api_provider_id)
VALUES ('mlb', 'Major League Baseball', 'MLB', 'baseball', 'USA',
        'https://a.espncdn.com/i/teamlogos/leagues/500/mlb.png', 1903, '10')
ON CONFLICT (league_id) DO NOTHING;

-- MLB 30팀 추가
INSERT INTO teams (team_id, league_id, name, korean_name, short_name, color_hex, secondary_color_hex, logo_url, stadium_name, website_url, api_provider_id, created_at, updated_at) VALUES
('mlb-ari', 'mlb', 'Arizona Diamondbacks',    '애리조나 다이아몬드백스',   'ARI', 'aa182c', '000000', 'https://a.espncdn.com/i/teamlogos/mlb/500/ari.png', 'Chase Field',              'https://www.mlb.com/dbacks',    '29', NOW(), NOW()),
('mlb-ath', 'mlb', 'Athletics',               '애슬레틱스',               'ATH', '003831', 'efb21e', 'https://a.espncdn.com/i/teamlogos/mlb/500/ath.png', 'Sutter Health Park',       'https://www.mlb.com/athletics', '11', NOW(), NOW()),
('mlb-atl', 'mlb', 'Atlanta Braves',          '애틀랜타 브레이브스',       'ATL', '0c2340', 'ba0c2f', 'https://a.espncdn.com/i/teamlogos/mlb/500/atl.png', 'Truist Park',              'https://www.mlb.com/braves',    '15', NOW(), NOW()),
('mlb-bal', 'mlb', 'Baltimore Orioles',       '볼티모어 오리올스',        'BAL', 'df4601', '000000', 'https://a.espncdn.com/i/teamlogos/mlb/500/bal.png', 'Oriole Park',              'https://www.mlb.com/orioles',   '1',  NOW(), NOW()),
('mlb-bos', 'mlb', 'Boston Red Sox',          '보스턴 레드삭스',          'BOS', '0d2b56', 'bd3039', 'https://a.espncdn.com/i/teamlogos/mlb/500/bos.png', 'Fenway Park',              'https://www.mlb.com/redsox',    '2',  NOW(), NOW()),
('mlb-chc', 'mlb', 'Chicago Cubs',            '시카고 컵스',              'CHC', '0e3386', 'cc3433', 'https://a.espncdn.com/i/teamlogos/mlb/500/chc.png', 'Wrigley Field',            'https://www.mlb.com/cubs',      '16', NOW(), NOW()),
('mlb-chw', 'mlb', 'Chicago White Sox',       '시카고 화이트삭스',        'CHW', '000000', 'c4ced4', 'https://a.espncdn.com/i/teamlogos/mlb/500/chw.png', 'Guaranteed Rate Field',    'https://www.mlb.com/whitesox',  '4',  NOW(), NOW()),
('mlb-cin', 'mlb', 'Cincinnati Reds',         '신시내티 레즈',            'CIN', 'c6011f', 'ffffff', 'https://a.espncdn.com/i/teamlogos/mlb/500/cin.png', 'Great American Ball Park', 'https://www.mlb.com/reds',      '17', NOW(), NOW()),
('mlb-cle', 'mlb', 'Cleveland Guardians',     '클리블랜드 가디언스',      'CLE', '002b5c', 'e31937', 'https://a.espncdn.com/i/teamlogos/mlb/500/cle.png', 'Progressive Field',       'https://www.mlb.com/guardians', '5',  NOW(), NOW()),
('mlb-col', 'mlb', 'Colorado Rockies',        '콜로라도 로키스',          'COL', '33006f', '000000', 'https://a.espncdn.com/i/teamlogos/mlb/500/col.png', 'Coors Field',              'https://www.mlb.com/rockies',   '27', NOW(), NOW()),
('mlb-det', 'mlb', 'Detroit Tigers',          '디트로이트 타이거스',      'DET', '0a2240', 'ff4713', 'https://a.espncdn.com/i/teamlogos/mlb/500/det.png', 'Comerica Park',            'https://www.mlb.com/tigers',    '6',  NOW(), NOW()),
('mlb-hou', 'mlb', 'Houston Astros',          '휴스턴 애스트로스',        'HOU', '002d62', 'eb6e1f', 'https://a.espncdn.com/i/teamlogos/mlb/500/hou.png', 'Minute Maid Park',         'https://www.mlb.com/astros',    '18', NOW(), NOW()),
('mlb-kc',  'mlb', 'Kansas City Royals',      '캔자스시티 로열스',        'KC',  '004687', '7ab2dd', 'https://a.espncdn.com/i/teamlogos/mlb/500/kc.png',  'Kauffman Stadium',         'https://www.mlb.com/royals',    '7',  NOW(), NOW()),
('mlb-laa', 'mlb', 'Los Angeles Angels',      'LA 에인절스',              'LAA', 'ba0021', 'c4ced4', 'https://a.espncdn.com/i/teamlogos/mlb/500/laa.png', 'Angel Stadium',            'https://www.mlb.com/angels',    '3',  NOW(), NOW()),
('mlb-lad', 'mlb', 'Los Angeles Dodgers',     'LA 다저스',                'LAD', '005a9c', 'ffffff', 'https://a.espncdn.com/i/teamlogos/mlb/500/lad.png', 'Dodger Stadium',           'https://www.mlb.com/dodgers',   '19', NOW(), NOW()),
('mlb-mia', 'mlb', 'Miami Marlins',           '마이애미 말린스',          'MIA', '00a3e0', '000000', 'https://a.espncdn.com/i/teamlogos/mlb/500/mia.png', 'loanDepot park',           'https://www.mlb.com/marlins',   '28', NOW(), NOW()),
('mlb-mil', 'mlb', 'Milwaukee Brewers',       '밀워키 브루어스',          'MIL', '13294b', 'ffc72c', 'https://a.espncdn.com/i/teamlogos/mlb/500/mil.png', 'American Family Field',    'https://www.mlb.com/brewers',   '8',  NOW(), NOW()),
('mlb-min', 'mlb', 'Minnesota Twins',         '미네소타 트윈스',          'MIN', '031f40', 'e20e32', 'https://a.espncdn.com/i/teamlogos/mlb/500/min.png', 'Target Field',             'https://www.mlb.com/twins',     '9',  NOW(), NOW()),
('mlb-nym', 'mlb', 'New York Mets',           '뉴욕 메츠',               'NYM', '002d72', 'ff5910', 'https://a.espncdn.com/i/teamlogos/mlb/500/nym.png', 'Citi Field',               'https://www.mlb.com/mets',      '21', NOW(), NOW()),
('mlb-nyy', 'mlb', 'New York Yankees',        '뉴욕 양키스',             'NYY', '132448', 'c4ced4', 'https://a.espncdn.com/i/teamlogos/mlb/500/nyy.png', 'Yankee Stadium',           'https://www.mlb.com/yankees',   '10', NOW(), NOW()),
('mlb-phi', 'mlb', 'Philadelphia Phillies',   '필라델피아 필리스',        'PHI', 'e81828', '003278', 'https://a.espncdn.com/i/teamlogos/mlb/500/phi.png', 'Citizens Bank Park',       'https://www.mlb.com/phillies',  '22', NOW(), NOW()),
('mlb-pit', 'mlb', 'Pittsburgh Pirates',      '피츠버그 파이리츠',        'PIT', '000000', 'fdb827', 'https://a.espncdn.com/i/teamlogos/mlb/500/pit.png', 'PNC Park',                 'https://www.mlb.com/pirates',   '23', NOW(), NOW()),
('mlb-sd',  'mlb', 'San Diego Padres',        '샌디에이고 파드리스',      'SD',  '2f241d', 'ffc425', 'https://a.espncdn.com/i/teamlogos/mlb/500/sd.png',  'Petco Park',               'https://www.mlb.com/padres',    '25', NOW(), NOW()),
('mlb-sf',  'mlb', 'San Francisco Giants',    '샌프란시스코 자이언츠',    'SF',  '000000', 'fd5a1e', 'https://a.espncdn.com/i/teamlogos/mlb/500/sf.png',  'Oracle Park',              'https://www.mlb.com/giants',    '26', NOW(), NOW()),
('mlb-sea', 'mlb', 'Seattle Mariners',        '시애틀 매리너스',          'SEA', '005c5c', '0c2c56', 'https://a.espncdn.com/i/teamlogos/mlb/500/sea.png', 'T-Mobile Park',            'https://www.mlb.com/mariners',  '12', NOW(), NOW()),
('mlb-stl', 'mlb', 'St. Louis Cardinals',     '세인트루이스 카디널스',    'STL', 'be0a14', '001541', 'https://a.espncdn.com/i/teamlogos/mlb/500/stl.png', 'Busch Stadium',            'https://www.mlb.com/cardinals', '24', NOW(), NOW()),
('mlb-tb',  'mlb', 'Tampa Bay Rays',          '탬파베이 레이스',          'TB',  '092c5c', '8fbce6', 'https://a.espncdn.com/i/teamlogos/mlb/500/tb.png',  'Tropicana Field',          'https://www.mlb.com/rays',      '30', NOW(), NOW()),
('mlb-tex', 'mlb', 'Texas Rangers',           '텍사스 레인저스',          'TEX', '003278', 'c0111f', 'https://a.espncdn.com/i/teamlogos/mlb/500/tex.png', 'Globe Life Field',         'https://www.mlb.com/rangers',   '13', NOW(), NOW()),
('mlb-tor', 'mlb', 'Toronto Blue Jays',       '토론토 블루제이스',        'TOR', '134a8e', '6cace5', 'https://a.espncdn.com/i/teamlogos/mlb/500/tor.png', 'Rogers Centre',            'https://www.mlb.com/bluejays',  '14', NOW(), NOW()),
('mlb-wsh', 'mlb', 'Washington Nationals',    '워싱턴 내셔널스',          'WSH', 'ab0003', '11225b', 'https://a.espncdn.com/i/teamlogos/mlb/500/wsh.png', 'Nationals Park',           'https://www.mlb.com/nationals', '20', NOW(), NOW())
ON CONFLICT (team_id) DO NOTHING;
