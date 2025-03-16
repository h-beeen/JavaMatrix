package kr.beeen.matrix.global.security.matcher;

import org.springframework.http.HttpMethod;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.OrRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;

import static kr.beeen.matrix.global.security.presentation.AuthUriConfig.*;

public class UnauthorizedRequestMatchers {

    public static RequestMatcher getMatchers() {
        return new OrRequestMatcher(
                unauthorizedUserMatchers()
        );
    }

    private static RequestMatcher unauthorizedUserMatchers() {
        return new OrRequestMatcher(
                new AntPathRequestMatcher(AUTH_BASE_URI + LOGIN, HttpMethod.POST.name()),
                new AntPathRequestMatcher(AUTH_BASE_URI + SIGNUP, HttpMethod.POST.name())
        );
    }
}
