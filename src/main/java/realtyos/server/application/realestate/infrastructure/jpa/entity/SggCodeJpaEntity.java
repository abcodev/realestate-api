package realtyos.server.application.realestate.infrastructure.jpa.entity;

import realtyos.server.application.common.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Table(name = "real_estate_sgg_code")
public class SggCodeJpaEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String sggCd;

    private String fullNm;

    private String sigKorNm;

    private String sigEngNm;
}
