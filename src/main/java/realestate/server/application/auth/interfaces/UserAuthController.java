package realestate.server.application.auth.interfaces;

import realestate.server.application.auth.application.UserAuthService;
import realestate.server.application.common.web.auth.CurrentUser;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserAuthController {

    private final UserAuthService userAuthService;

    @DeleteMapping
    public ResponseEntity<Boolean> withdraw(@CurrentUser Long userId) {
        boolean result = userAuthService.withdraw(userId);
        return ResponseEntity.ok(result);
    }

}
