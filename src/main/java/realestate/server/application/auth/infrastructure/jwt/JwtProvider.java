package realestate.server.application.auth.infrastructure.jwt;

import realestate.server.application.auth.domain.AuthToken;
import realestate.server.application.auth.domain.TokenProvider;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;

@Slf4j
@Component
public class JwtProvider implements TokenProvider {

    private final SecretKey key;
    private final long accessTokenValidityInMilliseconds = 1000L * 60 * 60; // 1h
    private final long refreshTokenValidityInMilliseconds = 1000L * 60 * 60 * 24 * 60; // 60d

    public JwtProvider(@Value("${jwt.secret}") String secretKey) {
        this.key = Keys.hmacShaKeyFor(secretKey.getBytes());
    }

    /**
     * 토큰 생성 (userType 클레임 포함)
     */
    @Override
    public AuthToken createToken(Long userId, String userType) {
        long now = (new Date()).getTime();

        Date accessTokenExpiresIn = new Date(now + accessTokenValidityInMilliseconds);
        String accessToken = Jwts.builder()
                .subject(userId.toString())
                .claim("userType", userType)
                .expiration(accessTokenExpiresIn)
                .signWith(key, Jwts.SIG.HS256)
                .compact();

        Date refreshTokenExpiresIn = new Date(now + refreshTokenValidityInMilliseconds);
        String refreshToken = Jwts.builder()
                .subject(userId.toString())
                .expiration(refreshTokenExpiresIn)
                .signWith(key, Jwts.SIG.HS256)
                .compact();

        return new AuthToken(accessToken, refreshToken);
    }

    /**
     * 토큰에서 userId(subject) 추출
     */
    @Override
    public Long parseUserId(String token) {
        Claims claims = parseClaims(token);
        return Long.parseLong(claims.getSubject());
    }

    /**
     * 토큰에서 userType 클레임 추출
     */
    @Override
    public String parseUserType(String token) {
        Claims claims = parseClaims(token);
        return claims.get("userType", String.class);
    }

    /**
     * 토큰 유효성 검증
     */
    @Override
    public boolean validateToken(String token) {
        try {
            Jwts.parser()
                    .verifyWith(key)
                    .build()
                    .parseSignedClaims(token);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            log.info("Invalid JWT token: {}", e.getMessage());
            return false;
        }
    }

    private Claims parseClaims(String token) {
        return Jwts.parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }
}

