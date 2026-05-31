package realtyos.server.application.common.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/**
 * Swagger / OpenAPI 3.0 설정.
 * <p>
 * 접속 URL:
 * <ul>
 *   <li>Swagger UI: <a href="http://localhost:8080/swagger-ui/index.html">http://localhost:8080/swagger-ui/index.html</a></li>
 *   <li>API Docs (JSON): <a href="http://localhost:8080/v3/api-docs">http://localhost:8080/v3/api-docs</a></li>
 * </ul>
 */
@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("realtyos API")
                        .description("부동산 RAG LLM AI 응답 서비스")
                        .version("v1.0.0")
                        .contact(new Contact()
                                .name("RealtyOs")
                                .url("https://github.com/abcodev/realtyos-api")
                        )
                )
                .servers(List.of(
                        new Server().url("http://localhost:8080").description("로컬 개발 서버")
                ));
    }
}
