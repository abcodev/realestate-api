package realtyos.server.application.common.web.auth;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
public @interface CurrentUser {

    /**
     * true(기본값)이면 인증되지 않은 요청 시 401 에러를 반환
     * false이면 인증되지 않은 요청에서 null을 주입
     */
    boolean required() default true;

}
