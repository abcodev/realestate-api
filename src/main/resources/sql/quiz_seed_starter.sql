-- =====================================================
-- 일본어 퀴즈 시드: STARTER (왕초보) - 100문항
-- 기본 인사, 히라가나, 숫자, 동물, 색깔, 음식, 가족 등
-- =====================================================

INSERT INTO quiz_questions (set_id, question_id, question, options, correct_option_index, explanation, sort_order) VALUES
-- === 기본 인사 (1~10) ===
((SELECT id FROM quiz_sets WHERE difficulty='STARTER' AND category='japanese'), 'jp_w_1', '"아침 인사"로 알맞은 것은?', '["곤니치와","오하요 고자이마스","곰방와","오야스미나사이"]', 1, '아침 인사는 "오하요 고자이마스(おはようございます)"입니다.', 1),
((SELECT id FROM quiz_sets WHERE difficulty='STARTER' AND category='japanese'), 'jp_w_2', '"감사합니다"의 일본어는?', '["스미마센","고멘나사이","아리가토 고자이마스","이타다키마스"]', 2, '"아리가토 고자이마스(ありがとうございます)"가 감사합니다입니다.', 2),
((SELECT id FROM quiz_sets WHERE difficulty='STARTER' AND category='japanese'), 'jp_w_3', '"네" (긍정)는 일본어로?', '["이이에","하이","도우조","다메"]', 1, '"하이(はい)"가 "네"입니다.', 3),
((SELECT id FROM quiz_sets WHERE difficulty='STARTER' AND category='japanese'), 'jp_w_4', '"아니요" (부정)는 일본어로?', '["하이","이이에","아레","소레"]', 1, '"이이에(いいえ)"가 "아니요"입니다.', 4),
((SELECT id FROM quiz_sets WHERE difficulty='STARTER' AND category='japanese'), 'jp_w_5', '헤어질 때 하는 인사는?', '["오하요","사요나라","이타다키마스","고치소사마"]', 1, '"사요나라(さようなら)"는 작별 인사입니다.', 5),
((SELECT id FROM quiz_sets WHERE difficulty='STARTER' AND category='japanese'), 'jp_w_6', '"미안합니다"는 일본어로?', '["아리가토","스미마센","오메데토","다이조부"]', 1, '"스미마센(すみません)"은 미안합니다입니다.', 6),
((SELECT id FROM quiz_sets WHERE difficulty='STARTER' AND category='japanese'), 'jp_w_7', '"안녕하세요" (낮 인사)는?', '["오하요","곤니치와","곰방와","사요나라"]', 1, '"곤니치와(こんにちは)"는 낮 인사입니다.', 7),
((SELECT id FROM quiz_sets WHERE difficulty='STARTER' AND category='japanese'), 'jp_w_8', '"안녕하세요" (저녁 인사)는?', '["오하요","곤니치와","곰방와","오야스미"]', 2, '"곰방와(こんばんは)"는 저녁 인사입니다.', 8),
((SELECT id FROM quiz_sets WHERE difficulty='STARTER' AND category='japanese'), 'jp_w_9', '"잘 자" (밤 인사)는?', '["오하요","곤니치와","곰방와","오야스미나사이"]', 3, '"오야스미나사이(おやすみなさい)"는 잘 자라는 인사입니다.', 9),
((SELECT id FROM quiz_sets WHERE difficulty='STARTER' AND category='japanese'), 'jp_w_10', '"잘 먹겠습니다"는?', '["이타다키마스","고치소사마","오메데토","간바테"]', 0, '"이타다키마스(いただきます)"는 식사 전 인사입니다.', 10),

-- === 숫자 (11~20) ===
((SELECT id FROM quiz_sets WHERE difficulty='STARTER' AND category='japanese'), 'jp_w_11', '숫자 "1"은 일본어로?', '["이치","니","산","시"]', 0, '1은 "이치(いち)"입니다.', 11),
((SELECT id FROM quiz_sets WHERE difficulty='STARTER' AND category='japanese'), 'jp_w_12', '숫자 "2"는 일본어로?', '["이치","니","산","시"]', 1, '2는 "니(に)"입니다.', 12),
((SELECT id FROM quiz_sets WHERE difficulty='STARTER' AND category='japanese'), 'jp_w_13', '숫자 "3"은 일본어로?', '["니","산","시","고"]', 1, '3은 "산(さん)"입니다.', 13),
((SELECT id FROM quiz_sets WHERE difficulty='STARTER' AND category='japanese'), 'jp_w_14', '숫자 "5"는 일본어로?', '["시","고","로쿠","나나"]', 1, '5는 "고(ご)"입니다.', 14),
((SELECT id FROM quiz_sets WHERE difficulty='STARTER' AND category='japanese'), 'jp_w_15', '숫자 "7"은 일본어로?', '["로쿠","나나","하치","큐"]', 1, '7은 "나나(なな)" 또는 "시치(しち)"입니다.', 15),
((SELECT id FROM quiz_sets WHERE difficulty='STARTER' AND category='japanese'), 'jp_w_16', '숫자 "10"은 일본어로?', '["하치","큐","쥬","햐쿠"]', 2, '10은 "쥬(じゅう)"입니다.', 16),
((SELECT id FROM quiz_sets WHERE difficulty='STARTER' AND category='japanese'), 'jp_w_17', '숫자 "100"은 일본어로?', '["쥬","햐쿠","센","만"]', 1, '100은 "햐쿠(ひゃく)"입니다.', 17),
((SELECT id FROM quiz_sets WHERE difficulty='STARTER' AND category='japanese'), 'jp_w_18', '숫자 "1000"은 일본어로?', '["햐쿠","센","만","이치만"]', 1, '1000은 "센(せん)"입니다.', 18),
((SELECT id FROM quiz_sets WHERE difficulty='STARTER' AND category='japanese'), 'jp_w_19', '숫자 "4"의 읽기로 맞는 것은?', '["시","욘","둘 다 맞음","산"]', 2, '4는 "시(し)" 또는 "욘(よん)" 둘 다 사용합니다.', 19),
((SELECT id FROM quiz_sets WHERE difficulty='STARTER' AND category='japanese'), 'jp_w_20', '숫자 "9"의 읽기로 맞는 것은?', '["큐","쿠","둘 다 맞음","하치"]', 2, '9는 "큐(きゅう)" 또는 "쿠(く)" 둘 다 사용합니다.', 20),

-- === 동물 (21~30) ===
((SELECT id FROM quiz_sets WHERE difficulty='STARTER' AND category='japanese'), 'jp_w_21', '"고양이"는 일본어로?', '["이누","네코","토리","사카나"]', 1, '고양이는 "네코(猫)"입니다.', 21),
((SELECT id FROM quiz_sets WHERE difficulty='STARTER' AND category='japanese'), 'jp_w_22', '"강아지"는 일본어로?', '["네코","이누","우마","우시"]', 1, '개는 "이누(犬)"입니다.', 22),
((SELECT id FROM quiz_sets WHERE difficulty='STARTER' AND category='japanese'), 'jp_w_23', '"새"는 일본어로?', '["사카나","토리","무시","카메"]', 1, '새는 "토리(鳥)"입니다.', 23),
((SELECT id FROM quiz_sets WHERE difficulty='STARTER' AND category='japanese'), 'jp_w_24', '"물고기"는 일본어로?', '["토리","사카나","무시","카에루"]', 1, '물고기는 "사카나(魚)"입니다.', 24),
((SELECT id FROM quiz_sets WHERE difficulty='STARTER' AND category='japanese'), 'jp_w_25', '"말"은 일본어로?', '["우시","우마","히츠지","부타"]', 1, '말은 "우마(馬)"입니다.', 25),
((SELECT id FROM quiz_sets WHERE difficulty='STARTER' AND category='japanese'), 'jp_w_26', '"소"는 일본어로?', '["우마","우시","부타","히츠지"]', 1, '소는 "우시(牛)"입니다.', 26),
((SELECT id FROM quiz_sets WHERE difficulty='STARTER' AND category='japanese'), 'jp_w_27', '"돼지"는 일본어로?', '["우시","우마","부타","히츠지"]', 2, '돼지는 "부타(豚)"입니다.', 27),
((SELECT id FROM quiz_sets WHERE difficulty='STARTER' AND category='japanese'), 'jp_w_28', '"토끼"는 일본어로?', '["카메","우사기","카에루","네즈미"]', 1, '토끼는 "우사기(うさぎ)"입니다.', 28),
((SELECT id FROM quiz_sets WHERE difficulty='STARTER' AND category='japanese'), 'jp_w_29', '"원숭이"는 일본어로?', '["사루","쿠마","시카","키츠네"]', 0, '원숭이는 "사루(猿)"입니다.', 29),
((SELECT id FROM quiz_sets WHERE difficulty='STARTER' AND category='japanese'), 'jp_w_30', '"곰"은 일본어로?', '["사루","쿠마","시카","타누키"]', 1, '곰은 "쿠마(熊)"입니다.', 30),

-- === 색깔 (31~40) ===
((SELECT id FROM quiz_sets WHERE difficulty='STARTER' AND category='japanese'), 'jp_w_31', '"빨간색"은 일본어로?', '["아카","아오","시로","쿠로"]', 0, '빨간색은 "아카(赤)"입니다.', 31),
((SELECT id FROM quiz_sets WHERE difficulty='STARTER' AND category='japanese'), 'jp_w_32', '"파란색"은 일본어로?', '["아카","아오","시로","쿠로"]', 1, '파란색은 "아오(青)"입니다.', 32),
((SELECT id FROM quiz_sets WHERE difficulty='STARTER' AND category='japanese'), 'jp_w_33', '"흰색"은 일본어로?', '["아카","아오","시로","쿠로"]', 2, '흰색은 "시로(白)"입니다.', 33),
((SELECT id FROM quiz_sets WHERE difficulty='STARTER' AND category='japanese'), 'jp_w_34', '"검은색"은 일본어로?', '["아카","아오","시로","쿠로"]', 3, '검은색은 "쿠로(黒)"입니다.', 34),
((SELECT id FROM quiz_sets WHERE difficulty='STARTER' AND category='japanese'), 'jp_w_35', '"노란색"은 일본어로?', '["미도리","키이로","무라사키","차이로"]', 1, '노란색은 "키이로(黄色)"입니다.', 35),
((SELECT id FROM quiz_sets WHERE difficulty='STARTER' AND category='japanese'), 'jp_w_36', '"초록색"은 일본어로?', '["미도리","키이로","무라사키","오렌지"]', 0, '초록색은 "미도리(緑)"입니다.', 36),
((SELECT id FROM quiz_sets WHERE difficulty='STARTER' AND category='japanese'), 'jp_w_37', '"보라색"은 일본어로?', '["미도리","키이로","무라사키","핑쿠"]', 2, '보라색은 "무라사키(紫)"입니다.', 37),
((SELECT id FROM quiz_sets WHERE difficulty='STARTER' AND category='japanese'), 'jp_w_38', '"갈색"은 일본어로?', '["차이로","하이이로","킨이로","긴이로"]', 0, '갈색은 "차이로(茶色)"입니다.', 38),
((SELECT id FROM quiz_sets WHERE difficulty='STARTER' AND category='japanese'), 'jp_w_39', '"회색"은 일본어로?', '["차이로","하이이로","킨이로","긴이로"]', 1, '회색은 "하이이로(灰色)"입니다.', 39),
((SELECT id FROM quiz_sets WHERE difficulty='STARTER' AND category='japanese'), 'jp_w_40', '"분홍색"은 일본어로?', '["무라사키","핑쿠","오렌지","미즈이로"]', 1, '분홍색은 "핑쿠(ピンク)"입니다.', 40),

-- === 음식/음료 (41~55) ===
((SELECT id FROM quiz_sets WHERE difficulty='STARTER' AND category='japanese'), 'jp_w_41', '"물"은 일본어로?', '["미즈","오차","규뉴","코히"]', 0, '물은 "미즈(水)"입니다.', 41),
((SELECT id FROM quiz_sets WHERE difficulty='STARTER' AND category='japanese'), 'jp_w_42', '"차"는 일본어로?', '["미즈","오차","규뉴","코히"]', 1, '차는 "오차(お茶)"입니다.', 42),
((SELECT id FROM quiz_sets WHERE difficulty='STARTER' AND category='japanese'), 'jp_w_43', '"우유"는 일본어로?', '["미즈","오차","규뉴","코히"]', 2, '우유는 "규뉴(牛乳)"입니다.', 43),
((SELECT id FROM quiz_sets WHERE difficulty='STARTER' AND category='japanese'), 'jp_w_44', '"커피"는 일본어로?', '["미즈","오차","규뉴","코히"]', 3, '커피는 "코히(コーヒー)"입니다.', 44),
((SELECT id FROM quiz_sets WHERE difficulty='STARTER' AND category='japanese'), 'jp_w_45', '"밥"은 일본어로?', '["고항","판","니쿠","야사이"]', 0, '밥은 "고항(ご飯)"입니다.', 45),
((SELECT id FROM quiz_sets WHERE difficulty='STARTER' AND category='japanese'), 'jp_w_46', '"빵"은 일본어로?', '["고항","판","니쿠","야사이"]', 1, '빵은 "판(パン)"입니다.', 46),
((SELECT id FROM quiz_sets WHERE difficulty='STARTER' AND category='japanese'), 'jp_w_47', '"고기"는 일본어로?', '["고항","판","니쿠","야사이"]', 2, '고기는 "니쿠(肉)"입니다.', 47),
((SELECT id FROM quiz_sets WHERE difficulty='STARTER' AND category='japanese'), 'jp_w_48', '"야채"는 일본어로?', '["니쿠","야사이","쿠다모노","사카나"]', 1, '야채는 "야사이(野菜)"입니다.', 48),
((SELECT id FROM quiz_sets WHERE difficulty='STARTER' AND category='japanese'), 'jp_w_49', '"과일"은 일본어로?', '["니쿠","야사이","쿠다모노","사카나"]', 2, '과일은 "쿠다모노(果物)"입니다.', 49),
((SELECT id FROM quiz_sets WHERE difficulty='STARTER' AND category='japanese'), 'jp_w_50', '"달걀"은 일본어로?', '["타마고","토후","노리","미소"]', 0, '달걀은 "타마고(卵)"입니다.', 50),
((SELECT id FROM quiz_sets WHERE difficulty='STARTER' AND category='japanese'), 'jp_w_51', '"라멘"은 일본어로?', '["우동","소바","라멘","돈부리"]', 2, '라멘은 "라멘(ラーメン)"입니다.', 51),
((SELECT id FROM quiz_sets WHERE difficulty='STARTER' AND category='japanese'), 'jp_w_52', '"초밥"은 일본어로?', '["스시","사시미","텐푸라","야키토리"]', 0, '초밥은 "스시(寿司)"입니다.', 52),
((SELECT id FROM quiz_sets WHERE difficulty='STARTER' AND category='japanese'), 'jp_w_53', '"맥주"는 일본어로?', '["사케","비루","와인","쥬스"]', 1, '맥주는 "비루(ビール)"입니다.', 53),
((SELECT id FROM quiz_sets WHERE difficulty='STARTER' AND category='japanese'), 'jp_w_54', '"사과"는 일본어로?', '["링고","미캉","이치고","부도"]', 0, '사과는 "링고(りんご)"입니다.', 54),
((SELECT id FROM quiz_sets WHERE difficulty='STARTER' AND category='japanese'), 'jp_w_55', '"딸기"는 일본어로?', '["링고","미캉","이치고","부도"]', 2, '딸기는 "이치고(いちご)"입니다.', 55),

-- === 가족/사람 (56~65) ===
((SELECT id FROM quiz_sets WHERE difficulty='STARTER' AND category='japanese'), 'jp_w_56', '"나(자신)"를 뜻하는 단어는?', '["아나타","카레","와타시","카노조"]', 2, '"와타시(私)"는 나를 뜻합니다.', 56),
((SELECT id FROM quiz_sets WHERE difficulty='STARTER' AND category='japanese'), 'jp_w_57', '"어머니"는 일본어로?', '["오카상","오토상","오니상","오네상"]', 0, '어머니는 "오카상(お母さん)"입니다.', 57),
((SELECT id FROM quiz_sets WHERE difficulty='STARTER' AND category='japanese'), 'jp_w_58', '"아버지"는 일본어로?', '["오카상","오토상","오니상","오지상"]', 1, '아버지는 "오토상(お父さん)"입니다.', 58),
((SELECT id FROM quiz_sets WHERE difficulty='STARTER' AND category='japanese'), 'jp_w_59', '"형/오빠"는 일본어로?', '["오카상","오토상","오니상","오네상"]', 2, '형/오빠는 "오니상(お兄さん)"입니다.', 59),
((SELECT id FROM quiz_sets WHERE difficulty='STARTER' AND category='japanese'), 'jp_w_60', '"누나/언니"는 일본어로?', '["오카상","오토상","오니상","오네상"]', 3, '누나/언니는 "오네상(お姉さん)"입니다.', 60),
((SELECT id FROM quiz_sets WHERE difficulty='STARTER' AND category='japanese'), 'jp_w_61', '"남동생"은 일본어로?', '["오토토","이모토","오지상","오바상"]', 0, '남동생은 "오토토(弟)"입니다.', 61),
((SELECT id FROM quiz_sets WHERE difficulty='STARTER' AND category='japanese'), 'jp_w_62', '"여동생"은 일본어로?', '["오토토","이모토","오지상","오바상"]', 1, '여동생은 "이모토(妹)"입니다.', 62),
((SELECT id FROM quiz_sets WHERE difficulty='STARTER' AND category='japanese'), 'jp_w_63', '"선생님"은 일본어로?', '["센세","가쿠세","이샤","카이샤인"]', 0, '선생님은 "센세(先生)"입니다.', 63),
((SELECT id FROM quiz_sets WHERE difficulty='STARTER' AND category='japanese'), 'jp_w_64', '"학생"은 일본어로?', '["센세","가쿠세","도모다치","카조쿠"]', 1, '학생은 "가쿠세(学生)"입니다.', 64),
((SELECT id FROM quiz_sets WHERE difficulty='STARTER' AND category='japanese'), 'jp_w_65', '"친구"는 일본어로?', '["센세","가쿠세","도모다치","카조쿠"]', 2, '친구는 "도모다치(友達)"입니다.', 65),

-- === 장소/건물 (66~75) ===
((SELECT id FROM quiz_sets WHERE difficulty='STARTER' AND category='japanese'), 'jp_w_66', '"학교"는 일본어로?', '["각코","뵤인","깅코","코엔"]', 0, '학교는 "각코(学校)"입니다.', 66),
((SELECT id FROM quiz_sets WHERE difficulty='STARTER' AND category='japanese'), 'jp_w_67', '"병원"은 일본어로?', '["각코","뵤인","깅코","코엔"]', 1, '병원은 "뵤인(病院)"입니다.', 67),
((SELECT id FROM quiz_sets WHERE difficulty='STARTER' AND category='japanese'), 'jp_w_68', '"은행"은 일본어로?', '["각코","뵤인","깅코","코엔"]', 2, '은행은 "깅코(銀行)"입니다.', 68),
((SELECT id FROM quiz_sets WHERE difficulty='STARTER' AND category='japanese'), 'jp_w_69', '"공원"은 일본어로?', '["각코","뵤인","깅코","코엔"]', 3, '공원은 "코엔(公園)"입니다.', 69),
((SELECT id FROM quiz_sets WHERE difficulty='STARTER' AND category='japanese'), 'jp_w_70', '"역"은 일본어로?', '["에키","쿠코","호텔루","콤비니"]', 0, '역은 "에키(駅)"입니다.', 70),
((SELECT id FROM quiz_sets WHERE difficulty='STARTER' AND category='japanese'), 'jp_w_71', '"공항"은 일본어로?', '["에키","쿠코","호텔루","콤비니"]', 1, '공항은 "쿠코(空港)"입니다.', 71),
((SELECT id FROM quiz_sets WHERE difficulty='STARTER' AND category='japanese'), 'jp_w_72', '"편의점"은 일본어로?', '["에키","쿠코","슈퍼","콤비니"]', 3, '편의점은 "콤비니(コンビニ)"입니다.', 72),
((SELECT id FROM quiz_sets WHERE difficulty='STARTER' AND category='japanese'), 'jp_w_73', '"우체국"은 일본어로?', '["유빙쿄쿠","토쇼캉","미세","레스토랑"]', 0, '우체국은 "유빙쿄쿠(郵便局)"입니다.', 73),
((SELECT id FROM quiz_sets WHERE difficulty='STARTER' AND category='japanese'), 'jp_w_74', '"도서관"은 일본어로?', '["유빙쿄쿠","토쇼캉","미세","레스토랑"]', 1, '도서관은 "토쇼캉(図書館)"입니다.', 74),
((SELECT id FROM quiz_sets WHERE difficulty='STARTER' AND category='japanese'), 'jp_w_75', '"가게"는 일본어로?', '["유빙쿄쿠","토쇼캉","미세","레스토랑"]', 2, '가게는 "미세(店)"입니다.', 75),

-- === 일상 사물 (76~85) ===
((SELECT id FROM quiz_sets WHERE difficulty='STARTER' AND category='japanese'), 'jp_w_76', '"책"은 일본어로?', '["혼","펜","노토","츠쿠에"]', 0, '책은 "혼(本)"입니다.', 76),
((SELECT id FROM quiz_sets WHERE difficulty='STARTER' AND category='japanese'), 'jp_w_77', '"펜"은 일본어로?', '["혼","펜","노토","츠쿠에"]', 1, '펜은 "펜(ペン)"입니다.', 77),
((SELECT id FROM quiz_sets WHERE difficulty='STARTER' AND category='japanese'), 'jp_w_78', '"노트"는 일본어로?', '["혼","펜","노토","츠쿠에"]', 2, '노트는 "노토(ノート)"입니다.', 78),
((SELECT id FROM quiz_sets WHERE difficulty='STARTER' AND category='japanese'), 'jp_w_79', '"책상"은 일본어로?', '["혼","이스","노토","츠쿠에"]', 3, '책상은 "츠쿠에(机)"입니다.', 79),
((SELECT id FROM quiz_sets WHERE difficulty='STARTER' AND category='japanese'), 'jp_w_80', '"의자"는 일본어로?', '["츠쿠에","이스","마도","도아"]', 1, '의자는 "이스(椅子)"입니다.', 80),
((SELECT id FROM quiz_sets WHERE difficulty='STARTER' AND category='japanese'), 'jp_w_81', '"전화"는 일본어로?', '["덴와","덴샤","덴키","텔레비"]', 0, '전화는 "덴와(電話)"입니다.', 81),
((SELECT id FROM quiz_sets WHERE difficulty='STARTER' AND category='japanese'), 'jp_w_82', '"시계"는 일본어로?', '["토게","카방","쿠츠","카사"]', 0, '시계는 "토게(時計)"입니다.', 82),
((SELECT id FROM quiz_sets WHERE difficulty='STARTER' AND category='japanese'), 'jp_w_83', '"가방"은 일본어로?', '["토게","카방","쿠츠","카사"]', 1, '가방은 "카방(かばん)"입니다.', 83),
((SELECT id FROM quiz_sets WHERE difficulty='STARTER' AND category='japanese'), 'jp_w_84', '"신발"은 일본어로?', '["토게","카방","쿠츠","카사"]', 2, '신발은 "쿠츠(靴)"입니다.', 84),
((SELECT id FROM quiz_sets WHERE difficulty='STARTER' AND category='japanese'), 'jp_w_85', '"우산"은 일본어로?', '["토게","카방","쿠츠","카사"]', 3, '우산은 "카사(傘)"입니다.', 85),

-- === 시간/날짜/자연 (86~100) ===
((SELECT id FROM quiz_sets WHERE difficulty='STARTER' AND category='japanese'), 'jp_w_86', '"오늘"은 일본어로?', '["아시타","키노","쿄","이마"]', 2, '오늘은 "쿄(今日)"입니다.', 86),
((SELECT id FROM quiz_sets WHERE difficulty='STARTER' AND category='japanese'), 'jp_w_87', '"내일"은 일본어로?', '["아시타","키노","쿄","라이슈"]', 0, '내일은 "아시타(明日)"입니다.', 87),
((SELECT id FROM quiz_sets WHERE difficulty='STARTER' AND category='japanese'), 'jp_w_88', '"어제"는 일본어로?', '["아시타","키노","쿄","라이슈"]', 1, '어제는 "키노(昨日)"입니다.', 88),
((SELECT id FROM quiz_sets WHERE difficulty='STARTER' AND category='japanese'), 'jp_w_89', '"일본"은 일본어로?', '["캉코쿠","추고쿠","니혼","아메리카"]', 2, '일본은 "니혼(日本)"입니다.', 89),
((SELECT id FROM quiz_sets WHERE difficulty='STARTER' AND category='japanese'), 'jp_w_90', '"맛있다"는 일본어로?', '["오이시이","마즈이","다카이","야스이"]', 0, '"오이시이(おいしい)"는 맛있다입니다.', 90),
((SELECT id FROM quiz_sets WHERE difficulty='STARTER' AND category='japanese'), 'jp_w_91', '"비싸다"는 일본어로?', '["야스이","다카이","히쿠이","히로이"]', 1, '"다카이(高い)"는 비싸다입니다.', 91),
((SELECT id FROM quiz_sets WHERE difficulty='STARTER' AND category='japanese'), 'jp_w_92', '"싸다"는 일본어로?', '["야스이","다카이","히쿠이","히로이"]', 0, '"야스이(安い)"는 싸다입니다.', 92),
((SELECT id FROM quiz_sets WHERE difficulty='STARTER' AND category='japanese'), 'jp_w_93', '"크다"는 일본어로?', '["오키이","치사이","나가이","미지카이"]', 0, '"오키이(大きい)"는 크다입니다.', 93),
((SELECT id FROM quiz_sets WHERE difficulty='STARTER' AND category='japanese'), 'jp_w_94', '"작다"는 일본어로?', '["오키이","치사이","나가이","미지카이"]', 1, '"치사이(小さい)"는 작다입니다.', 94),
((SELECT id FROM quiz_sets WHERE difficulty='STARTER' AND category='japanese'), 'jp_w_95', '"하늘"은 일본어로?', '["소라","우미","야마","카와"]', 0, '하늘은 "소라(空)"입니다.', 95),
((SELECT id FROM quiz_sets WHERE difficulty='STARTER' AND category='japanese'), 'jp_w_96', '"바다"는 일본어로?', '["소라","우미","야마","카와"]', 1, '바다는 "우미(海)"입니다.', 96),
((SELECT id FROM quiz_sets WHERE difficulty='STARTER' AND category='japanese'), 'jp_w_97', '"산"은 일본어로?', '["소라","우미","야마","카와"]', 2, '산은 "야마(山)"입니다.', 97),
((SELECT id FROM quiz_sets WHERE difficulty='STARTER' AND category='japanese'), 'jp_w_98', '"강"은 일본어로?', '["소라","우미","야마","카와"]', 3, '강은 "카와(川)"입니다.', 98),
((SELECT id FROM quiz_sets WHERE difficulty='STARTER' AND category='japanese'), 'jp_w_99', '"꽃"은 일본어로?', '["하나","키","쿠사","모리"]', 0, '꽃은 "하나(花)"입니다.', 99),
((SELECT id FROM quiz_sets WHERE difficulty='STARTER' AND category='japanese'), 'jp_w_100', '"나무"는 일본어로?', '["하나","키","쿠사","모리"]', 1, '나무는 "키(木)"입니다.', 100)
ON CONFLICT (question_id) DO NOTHING;
