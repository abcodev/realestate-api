package realestate.server.application.realestate.infrastructure.jpa.entity;

import realestate.server.application.common.entity.BaseEntity;
import realestate.server.application.interest.infrastructure.jpa.entity.UserInterestJpaEntity;
import realestate.server.application.realestate.domain.InterestRegion;
import realestate.server.application.realestate.domain.RegionCode;
import jakarta.persistence.*;
import lombok.*;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Table(name = "user_interest_real_estate")
public class InterestRegionJpaEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "interest_id", nullable = false)
    private UserInterestJpaEntity interest;

    private String bgdCode;

    private String sggCode;

    private String umdCode;

    public InterestRegionJpaEntity(UserInterestJpaEntity interest, String bgdCode, String sggCode, String umdCode) {
        this.interest = interest;
        this.bgdCode = bgdCode;
        this.sggCode = sggCode;
        this.umdCode = umdCode;
    }

    public void changeRegion(RegionCode regionCode) {
        this.bgdCode = regionCode.getFullRegionCode();
        this.sggCode = regionCode.sggCode();
        this.umdCode = regionCode.umdCode();
    }

    public InterestRegion toDomain() {
        return InterestRegion.of(id, interest.getInterestId(), bgdCode, sggCode, umdCode);
    }
}
