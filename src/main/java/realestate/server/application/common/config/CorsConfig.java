package realestate.server.application.common.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.util.List;

/**
 * CORS(Cross-Origin Resource Sharing) 설정.
 * <p>
 * 이 API 서버는 Flutter 앱에서 호출되므로 CORS가 필수는 아닙니다.
 * (CORS는 브라우저에서만 적용되는 보안 정책이며, 네이티브 앱은 영향받지 않습니다.)
 * <p>
 * Swagger UI 또는 웹 브라우저 기반 테스트 도구(Postman Web 등) 사용 시를 대비한
 * 최소한의 CORS 설정만 포함합니다.
 */
@Configuration
public class CorsConfig {

    @Bean
    public CorsFilter corsFilter() {
        CorsConfiguration config = new CorsConfiguration();

        // 모든 Origin 허용 (네이티브 앱 기반이므로 CORS 제한 불필요)
        config.setAllowedOriginPatterns(List.of("*"));

        // 허용할 HTTP 메서드
        config.setAllowedMethods(List.of("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS"));

        // 허용할 요청 헤더
        config.setAllowedHeaders(List.of("*"));

        // 인증 정보 포함 허용
        config.setAllowCredentials(true);

        // preflight 요청 캐시 시간 (초) - 1시간
        config.setMaxAge(3600L);

        // 모든 경로에 적용
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);

        return new CorsFilter(source);
    }
}
