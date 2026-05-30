package realestate.server.application.common.infrastructure.jpa.entity;

import realestate.server.application.common.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

/**
 * 스크래핑 설정을 관리하는 테이블.
 * <p>
 * HTML 파싱과 JSON API 두 가지 방식의 설정을 하나의 테이블로 통합하여 관리합니다.
 * HTML 방식에서는 rowSelector/titleSelector/dateSelector를 사용하고,
 * JSON API 방식에서는 dataPath/titleField/dateField를 사용합니다.
 */
@Entity
@Table(name = "scrap_config")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
@Builder
public class ScrapConfigJpaEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /** 도메인 구분 (SPORT, FINANCE, NEWS) */
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private ScrapType type;

    /** 스크래핑 방식 (HTML, API) */
    @Column(name = "scrape_method", nullable = false, length = 10)
    private String scrapeMethod;

    /** DB 내 식별 코드 (예: "samsung", "kia") */
    @Column(name = "team_id", nullable = false, length = 50)
    private String teamId;

    /** 사이트 이름 (로그/디버깅용, 예: "삼성 라이온즈") */
    @Column(name = "site_name", nullable = false, length = 100)
    private String siteName;

    /** URL 매칭에 사용할 호스트 패턴 (예: "samsunglions.com") */
    @Column(name = "host_pattern", nullable = false, length = 200)
    private String hostPattern;

    /** 스크래핑 대상 URL */
    @Column(name = "target_url", nullable = false, length = 500)
    private String targetUrl;

    // ── HTML 파싱 전용 필드 ──────────────────────────────

    /** 게시글 행을 선택하는 CSS 셀렉터 */
    @Column(name = "row_selector", length = 200)
    private String rowSelector;

    /** 제목을 선택하는 CSS 셀렉터 */
    @Column(name = "title_selector", length = 200)
    private String titleSelector;

    /** 작성일을 선택하는 CSS 셀렉터 */
    @Column(name = "date_selector", length = 200)
    private String dateSelector;

    // ── JSON API 전용 필드 ──────────────────────────────

    /** JSON 루트에서 데이터 배열까지의 경로 (콤마 구분, 예: "data,list") */
    @Column(name = "data_path", length = 200)
    private String dataPath;

    /** 뉴스 제목 JSON 필드명 (예: "artcTitle") */
    @Column(name = "title_field", length = 100)
    private String titleField;

    /** 작성일 JSON 필드명 (예: "regDttm") */
    @Column(name = "date_field", length = 100)
    private String dateField;

    /**
     * dataPath를 String[]로 변환합니다.
     * DB에는 "data,list" 형태로 저장되며, 런타임에 ["data", "list"]로 분할합니다.
     */
    public String[] getDataPathArray() {
        if (dataPath == null || dataPath.isBlank()) {
            return new String[0];
        }
        return dataPath.split(",");
    }

    /**
     * HTML 파싱 방식인지 판별합니다.
     */
    public boolean isHtmlBased() {
        return "HTML".equalsIgnoreCase(scrapeMethod);
    }
}
