-- EPL 리그 추가
INSERT INTO leagues (league_id, name, korean_name, sport_type, country, logo_url, founded_year, api_provider_id)
VALUES ('epl', 'English Premier League', 'EPL', 'soccer', 'GBR',
        'https://a.espncdn.com/i/teamlogos/leagues/500/eng.1.png', 1992, '700')
ON CONFLICT (league_id) DO NOTHING;

-- EPL 20팀 추가
INSERT INTO teams (team_id, league_id, name, korean_name, short_name, color_hex, secondary_color_hex, logo_url, stadium_name, website_url, api_provider_id, created_at, updated_at) VALUES
('epl-bou', 'epl', 'AFC Bournemouth',           '본머스',              'BOU', 'f42727', 'ffffff', 'https://a.espncdn.com/i/teamlogos/soccer/500/349.png', 'Vitality Stadium',           'https://www.afcb.co.uk',         '349', NOW(), NOW()),
('epl-ars', 'epl', 'Arsenal',                   '아스널',              'ARS', 'e20520', '132257', 'https://a.espncdn.com/i/teamlogos/soccer/500/359.png', 'Emirates Stadium',            'https://www.arsenal.com',        '359', NOW(), NOW()),
('epl-avl', 'epl', 'Aston Villa',               '애스턴 빌라',         'AVL', '660e36', 'ffffff', 'https://a.espncdn.com/i/teamlogos/soccer/500/362.png', 'Villa Park',                  'https://www.avfc.co.uk',         '362', NOW(), NOW()),
('epl-bre', 'epl', 'Brentford',                 '브렌트퍼드',          'BRE', 'f42727', 'f8ced9', 'https://a.espncdn.com/i/teamlogos/soccer/500/337.png', 'Gtech Community Stadium',     'https://www.brentfordfc.com',    '337', NOW(), NOW()),
('epl-bha', 'epl', 'Brighton & Hove Albion',    '브라이턴',            'BHA', '0606fa', 'ffdd00', 'https://a.espncdn.com/i/teamlogos/soccer/500/331.png', 'Amex Stadium',                'https://www.brightonandhovealbion.com', '331', NOW(), NOW()),
('epl-bur', 'epl', 'Burnley',                   '번리',                'BUR', '6C1D45', '1a1a1a', 'https://a.espncdn.com/i/teamlogos/soccer/500/379.png', 'Turf Moor',                   'https://www.burnleyfc.com',      '379', NOW(), NOW()),
('epl-che', 'epl', 'Chelsea',                   '첼시',                'CHE', '144992', 'ffeeee', 'https://a.espncdn.com/i/teamlogos/soccer/500/363.png', 'Stamford Bridge',             'https://www.chelseafc.com',      '363', NOW(), NOW()),
('epl-cry', 'epl', 'Crystal Palace',            '크리스탈 팰리스',     'CRY', '0202fb', 'ffdd00', 'https://a.espncdn.com/i/teamlogos/soccer/500/384.png', 'Selhurst Park',               'https://www.cpfc.co.uk',         '384', NOW(), NOW()),
('epl-eve', 'epl', 'Everton',                   '에버턴',              'EVE', '0606fa', '132257', 'https://a.espncdn.com/i/teamlogos/soccer/500/368.png', 'Goodison Park',               'https://www.evertonfc.com',      '368', NOW(), NOW()),
('epl-ful', 'epl', 'Fulham',                    '풀럼',                'FUL', 'ffffff', 'd11317', 'https://a.espncdn.com/i/teamlogos/soccer/500/370.png', 'Craven Cottage',              'https://www.fulhamfc.com',       '370', NOW(), NOW()),
('epl-lee', 'epl', 'Leeds United',              '리즈 유나이티드',     'LEE', 'ffffff', 'ffff00', 'https://a.espncdn.com/i/teamlogos/soccer/500/357.png', 'Elland Road',                 'https://www.leedsunited.com',    '357', NOW(), NOW()),
('epl-liv', 'epl', 'Liverpool',                 '리버풀',              'LIV', 'd11317', '132257', 'https://a.espncdn.com/i/teamlogos/soccer/500/364.png', 'Anfield',                     'https://www.liverpoolfc.com',    '364', NOW(), NOW()),
('epl-mnc', 'epl', 'Manchester City',           '맨체스터 시티',       'MNC', '99c5ea', 'e6ff00', 'https://a.espncdn.com/i/teamlogos/soccer/500/382.png', 'Etihad Stadium',              'https://www.mancity.com',        '382', NOW(), NOW()),
('epl-man', 'epl', 'Manchester United',         '맨체스터 유나이티드', 'MAN', 'da020e', '144992', 'https://a.espncdn.com/i/teamlogos/soccer/500/360.png', 'Old Trafford',                'https://www.manutd.com',         '360', NOW(), NOW()),
('epl-new', 'epl', 'Newcastle United',          '뉴캐슬 유나이티드',   'NEW', '000000', 'cd1937', 'https://a.espncdn.com/i/teamlogos/soccer/500/361.png', 'St James'' Park',             'https://www.nufc.co.uk',         '361', NOW(), NOW()),
('epl-nfo', 'epl', 'Nottingham Forest',         '노팅엄 포레스트',     'NFO', 'c8102e', '132257', 'https://a.espncdn.com/i/teamlogos/soccer/500/393.png', 'City Ground',                 'https://www.nottinghamforest.co.uk', '393', NOW(), NOW()),
('epl-sun', 'epl', 'Sunderland',                '선더랜드',            'SUN', 'EB172B', '87cced', 'https://a.espncdn.com/i/teamlogos/soccer/500/366.png', 'Stadium of Light',            'https://www.safc.com',           '366', NOW(), NOW()),
('epl-tot', 'epl', 'Tottenham Hotspur',         '토트넘 홋스퍼',       'TOT', 'ffffff', '9bafd8', 'https://a.espncdn.com/i/teamlogos/soccer/500/367.png', 'Tottenham Hotspur Stadium',   'https://www.tottenhamhotspur.com', '367', NOW(), NOW()),
('epl-whu', 'epl', 'West Ham United',           '웨스트햄 유나이티드', 'WHU', '7c2c3b', '000000', 'https://a.espncdn.com/i/teamlogos/soccer/500/371.png', 'London Stadium',              'https://www.whufc.com',          '371', NOW(), NOW()),
('epl-wol', 'epl', 'Wolverhampton Wanderers',   '울버햄튼',            'WOL', 'fdb913', 'cd1937', 'https://a.espncdn.com/i/teamlogos/soccer/500/380.png', 'Molineux Stadium',            'https://www.wolves.co.uk',       '380', NOW(), NOW())
ON CONFLICT (team_id) DO NOTHING;
