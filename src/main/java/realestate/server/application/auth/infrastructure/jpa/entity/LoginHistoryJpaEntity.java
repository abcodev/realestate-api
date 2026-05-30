package realestate.server.application.auth.infrastructure.jpa.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "user_login_history")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Getter
public class LoginHistoryJpaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(name = "login_ip", length = 45)
    private String loginIp;

    @Builder.Default
    @Column(name = "login_at", nullable = false, updatable = false)
    private LocalDateTime loginAt = LocalDateTime.now();
}
