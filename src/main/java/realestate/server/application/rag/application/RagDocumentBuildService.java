package realestate.server.application.rag.application;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class RagDocumentBuildService {

    private static final String DEAL_SOURCE_TYPE = "DEAL";

    private final JdbcTemplate jdbcTemplate;

    @Transactional
    public RagDocumentBuildResult buildDealDocuments(int limit) {
        String sql = """
                INSERT INTO rag_document (
                    title,
                    content,
                    apartment_name,
                    region,
                    source_type,
                    source_id
                )
                SELECT
                    concat_ws(' ',
                        coalesce(nullif(d.apt_name, ''), '아파트명 미상'),
                        '실거래가',
                        concat(
                            coalesce(d.deal_year::text, '연도미상'), '년 ',
                            coalesce(d.deal_month::text, '월미상'), '월 ',
                            coalesce(d.deal_day::text, '일미상'), '일'
                        )
                    ) AS title,
                    concat_ws(E'\\n',
                        '문서유형: 아파트 실거래가',
                        concat('거래일: ', coalesce(d.deal_year::text, '연도미상'), '-', coalesce(d.deal_month::text, '월미상'), '-', coalesce(d.deal_day::text, '일미상')),
                        concat('아파트명: ', coalesce(nullif(d.apt_name, ''), '정보없음')),
                        concat('법정동: ', coalesce(nullif(d.umd_name, ''), '정보없음')),
                        concat('지번: ', coalesce(nullif(d.jibun, ''), '정보없음')),
                        concat('도로명: ', coalesce(nullif(d.road_name, ''), '정보없음')),
                        concat('전용면적: ', coalesce(nullif(d.exclu_use_area, ''), '정보없음'), '㎡'),
                        concat('거래금액: ', coalesce(nullif(d.deal_amount, ''), '정보없음'), '만원'),
                        concat('층: ', coalesce(nullif(d.floor, ''), '정보없음')),
                        concat('건축년도: ', coalesce(nullif(d.build_year, ''), '정보없음')),
                        concat('거래유형: ', coalesce(nullif(d.dealing_type, ''), '정보없음')),
                        concat('중개사소재지: ', coalesce(nullif(d.estate_agent_sgg_name, ''), '정보없음')),
                        concat('매도자유형: ', coalesce(nullif(d.sler_type, ''), '정보없음')),
                        concat('매수자유형: ', coalesce(nullif(d.buyer_type, ''), '정보없음')),
                        concat('토지임대부여부: ', coalesce(nullif(d.land_leasehold_type, ''), '정보없음')),
                        concat('실거래 원본 ID: ', d.id::text)
                    ) AS content,
                    nullif(d.apt_name, '') AS apartment_name,
                    concat_ws(' ', nullif(d.sgg_code, ''), nullif(d.umd_name, '')) AS region,
                    ? AS source_type,
                    d.id AS source_id
                FROM real_estate_deals d
                WHERE d.id IS NOT NULL
                AND NOT EXISTS (
                    SELECT 1
                    FROM rag_document rd
                    WHERE rd.source_type = ?
                    AND rd.source_id = d.id
                )
                ORDER BY d.id
                """;

        int insertedCount;
        if (limit > 0) {
            insertedCount = jdbcTemplate.update(sql + " LIMIT ? ON CONFLICT (source_type, source_id) DO NOTHING", DEAL_SOURCE_TYPE, DEAL_SOURCE_TYPE, limit);
        } else {
            insertedCount = jdbcTemplate.update(sql + " ON CONFLICT (source_type, source_id) DO NOTHING", DEAL_SOURCE_TYPE, DEAL_SOURCE_TYPE);
        }

        log.info("RAG deal document build completed - inserted: {}, limit: {}", insertedCount, limit);
        return new RagDocumentBuildResult(insertedCount);
    }
}
