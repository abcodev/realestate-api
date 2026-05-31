package realtyos.server.application.auth.interfaces;

import realtyos.server.application.auth.application.UserAuthService;
import realtyos.server.application.auth.domain.Oauth2Provider;
import realtyos.server.application.auth.interfaces.dto.*;
import realtyos.server.application.common.response.ApiResponse;
import realtyos.server.application.common.web.auth.CurrentUser;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Auth", description = "Authentication APIs")
@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class LoginController {

    private final UserAuthService userAuthService;

    @Operation(summary = "소셜 회원가입", description = "소셜 계정으로 회원가입을 처리합니다.")
    @PostMapping("/signup/{provider}")
    public ResponseEntity<LoginResponse> signup(
            @PathVariable String provider,
            @RequestBody UserRegisterRequest request) {
        Oauth2Provider oauth2Provider = Oauth2Provider.from(provider.toUpperCase());
        LoginResponse response = userAuthService.signup(request, oauth2Provider);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "소셜 로그인", description = "소셜 로그인을 처리하고 토큰을 발급합니다. 미가입 유저는 자동 회원가입됩니다.")
    @PostMapping("/login/{provider}")
    public ResponseEntity<LoginResponse> login(
            @PathVariable String provider,
            @RequestBody LoginRequest request,
            HttpServletRequest httpRequest) {
        Oauth2Provider oauth2Provider = Oauth2Provider.from(provider.toUpperCase());
        String clientIp = resolveClientIp(httpRequest);
        return userAuthService
                .login(request, oauth2Provider, clientIp)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(401).build());
    }

    @Operation(summary = "토큰 재발급", description = "Refresh Token으로 Access/Refresh Token을 재발급합니다.")
    @PostMapping("/reissue")
    public ResponseEntity<LoginResponse> reissue(@RequestBody TokenReissueRequest request) {
        LoginResponse response = userAuthService.reissue(request.refreshToken());
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "로그아웃", description = "Refresh Token을 무효화하여 로그아웃합니다.")
    @PostMapping("/logout")
    public ApiResponse<Void> logout(@CurrentUser Long userId) {
        userAuthService.logout(userId);
        return ApiResponse.empty();
    }

    private String resolveClientIp(HttpServletRequest request) {
        String xff = request.getHeader("X-Forwarded-For");
        if (xff != null && !xff.isBlank()) {
            return xff.split(",")[0].trim();
        }
        return request.getRemoteAddr();
    }
}
