package realestate.server.application.realestate.infrastructure.jpa.entity;

import realestate.server.application.common.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "realestate_news_summary", uniqueConstraints = @UniqueConstraint(name = "uq_realestate_news_summary_date", columnNames = {"summary_date"}))
public class RealEstateNewsSummaryJpaEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "summary_date", nullable = false)
    private LocalDate summaryDate;

    @Column(name = "one_line_brief", length = 500)
    private String oneLineBrief;

    @Column(name = "summary", columnDefinition = "TEXT")
    private String summary;

    @Builder
    public RealEstateNewsSummaryJpaEntity(Long id, LocalDate summaryDate, String oneLineBrief, String summary) {
        this.id = id;
        this.summaryDate = summaryDate;
        this.oneLineBrief = oneLineBrief;
        this.summary = summary;
    }
}
