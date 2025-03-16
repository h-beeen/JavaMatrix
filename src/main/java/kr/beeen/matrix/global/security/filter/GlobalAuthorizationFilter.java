package kr.beeen.matrix.global.security.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import kr.beeen.matrix.exception.BusinessException;
import kr.beeen.matrix.global.security.error.SecurityError;
import kr.beeen.matrix.global.security.jwt.JwtFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Optional;

@Slf4j
@Component
@RequiredArgsConstructor
public class GlobalAuthorizationFilter extends OncePerRequestFilter {

    private final static String AUTHORIZATION = "Authorization";
    private final static String TOKEN_PREFIX = "Bearer ";

    private final JwtFactory jwtFactory;

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain chain
    ) throws ServletException, IOException {
        String bearerToken = request.getHeader(AUTHORIZATION);
        String accessToken = extractValidBearerToken(bearerToken);

        Authentication authentication = jwtFactory.getAuthentication(request, accessToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        chain.doFilter(request, response);
    }

    private String extractValidBearerToken(String headerValue) {
        String bearerToken = Optional.ofNullable(headerValue)
                .filter(value -> value.startsWith(TOKEN_PREFIX))
                .orElseThrow(() -> headerValue == null
                        ? BusinessException.from(SecurityError.NULL_AUTHORIZATION)
                        : BusinessException.from(SecurityError.UNSUPPORTED_JWT)
                );

        return bearerToken.substring(TOKEN_PREFIX.length());
    }

    @Bean
    public FilterRegistrationBean<GlobalAuthorizationFilter> filterRegistrationBean() {
        FilterRegistrationBean<GlobalAuthorizationFilter> filterRegistrationBean = new FilterRegistrationBean<>();
        filterRegistrationBean.setFilter(this);
        filterRegistrationBean.setEnabled(false);
        return filterRegistrationBean;
    }
}
