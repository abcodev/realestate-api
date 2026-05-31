package realtyos.server.application.common.web.interceptor;

import realtyos.server.application.common.web.filter.CachedBodyWrappingFilter;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;

import java.nio.charset.StandardCharsets;
import java.util.Optional;

/**
 * 모든 API 요청/응답을 JSON 형태로 로깅하는 인터셉터.
 * <p>
 * CachedBodyWrappingFilter와 함께 동작하며,
 * Request Body와 Response Body를 읽어서 사람이 읽기 쉬운 형태로 출력합니다.
 */
@Slf4j
@Component
public class ApiLoggingInterceptor implements HandlerInterceptor {

    private static final ObjectMapper objectMapper = new ObjectMapper()
            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
            .configure(SerializationFeature.INDENT_OUTPUT, false);

    @Override
    public void afterCompletion(
            HttpServletRequest request, HttpServletResponse response,
            Object handler, Exception ex) throws Exception {

        // 소요 시간 계산
        long startTime = (long) Optional.ofNullable(
                request.getAttribute(CachedBodyWrappingFilter.ATTR_START_TIME))
                .orElse(System.currentTimeMillis());
        long durationMs = System.currentTimeMillis() - startTime;

        String requestId = (String) request.getAttribute(CachedBodyWrappingFilter.ATTR_REQUEST_ID);
        String method = request.getMethod();
        String path = request.getRequestURI();
        String query = Optional.ofNullable(request.getQueryString()).orElse("");
        int status = response.getStatus();

        // Request Body 추출
        String requestBody = "";
        if (request instanceof ContentCachingRequestWrapper wrapper) {
            requestBody = new String(wrapper.getContentAsByteArray(), StandardCharsets.UTF_8);
        }

        // Response Body 추출
        String responseBody = "";
        if (response instanceof ContentCachingResponseWrapper wrapper) {
            responseBody = new String(wrapper.getContentAsByteArray(), StandardCharsets.UTF_8);
        }

        // JSON 파싱 시도 (보기 좋게)
        String reqDisplay = toPrettyJson(requestBody);
        String resDisplay = toPrettyJson(responseBody);

        // 로그 출력
        StringBuilder sb = new StringBuilder();
        sb.append("\n┌─── API Log ───────────────────────────────────────\n");
        sb.append("│ ").append(method).append(" ").append(path);
        if (!query.isEmpty()) sb.append("?").append(query);
        sb.append("\n");
        sb.append("│ Status: ").append(status).append(" | Duration: ").append(durationMs).append("ms");
        if (requestId != null) sb.append(" | ID: ").append(requestId.substring(0, 8));
        sb.append("\n");

        if (!requestBody.isEmpty()) {
            sb.append("│ Request Body:\n");
            for (String line : reqDisplay.split("\n")) {
                sb.append("│   ").append(line).append("\n");
            }
        }

        if (!responseBody.isEmpty()) {
            // 응답이 너무 길면 앞부분만 표시
            String truncated = resDisplay.length() > 1000
                    ? resDisplay.substring(0, 1000) + "\n... (truncated)"
                    : resDisplay;
            sb.append("│ Response Body:\n");
            for (String line : truncated.split("\n")) {
                sb.append("│   ").append(line).append("\n");
            }
        }

        if (ex != null) {
            sb.append("│ ⚠ Error: ").append(ex.getClass().getSimpleName())
              .append(" - ").append(ex.getMessage()).append("\n");
        }

        sb.append("└───────────────────────────────────────────────────");

        if (status >= 400) {
            log.warn(sb.toString());
        } else {
            log.info(sb.toString());
        }
    }

    /**
     * JSON 문자열을 pretty-print 형태로 변환합니다.
     * JSON이 아닌 경우 원본 문자열을 그대로 반환합니다.
     */
    private String toPrettyJson(String raw) {
        if (raw == null || raw.isBlank()) return "";
        try {
            JsonNode node = objectMapper.readTree(raw);
            return objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(node);
        } catch (Exception e) {
            return raw; // JSON이 아니면 원본 그대로
        }
    }
}