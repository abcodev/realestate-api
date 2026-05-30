package realestate.server.application.common.web.filter;

import realestate.server.application.auth.infrastructure.jwt.JwtProvider;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

/**
 * JWT 인증 필터.
 * Authorization 헤더에서 Bearer 토큰을 추출하고 유효하면 userId를 request attribute에 저장합니다.
 * 토큰이 없거나 유효하지 않으면 userId는 null로 유지됩니다 (인증 필수 여부는 컨트롤러/Resolver에서 판단).
 */
@Slf4j
@Component
@Order(10)
@RequiredArgsConstructor
public class JwtAuthFilter extends OncePerRequestFilter {

    public static final String USER_ID_ATTRIBUTE = "userId";
    private static final String AUTHORIZATION_HEADER = "Authorization";
    private static final String BEARER_PREFIX = "Bearer ";

    private final JwtProvider jwtProvider;

    @Override
    protected void doFilterInternal(
            HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        String token = resolveToken(request);

        if (token != null && jwtProvider.validateToken(token)) {
            Long userId = jwtProvider.parseUserId(token);
            request.setAttribute(USER_ID_ATTRIBUTE, userId);
            log.debug("Authenticated userId: {}", userId);
        }

        filterChain.doFilter(request, response);
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        String path = request.getRequestURI();
        // 인증 관련 경로는 필터 skip (단, logout은 userId
        if (path.equals("/api/v1/auth/logout")) {
            return false;
        }
        return path.startsWith("/api/v1/auth/");
    }

    private String resolveToken(HttpServletRequest request) {
        String bearerToken = request.getHeader(AUTHORIZATION_HEADER);
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith(BEARER_PREFIX)) {
            return bearerToken.substring(BEARER_PREFIX.length());
        }
        return null;
    }
}
