package kr.beeen.matrix.global.security.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import kr.beeen.matrix.global.security.dto.LoginDto;
import kr.beeen.matrix.global.security.handler.GlobalAuthenticationFailureHandler;
import kr.beeen.matrix.global.security.handler.GlobalAuthenticationSuccessHandler;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;

import java.io.IOException;

import static kr.beeen.matrix.global.security.presentation.AuthUriConfig.AUTH_BASE_URI;
import static kr.beeen.matrix.global.security.presentation.AuthUriConfig.LOGIN;

@Component
public class LoginAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final ObjectMapper objectMapper;

    public LoginAuthenticationFilter(
            AuthenticationManager authenticationManager,
            ObjectMapper objectMapper,
            GlobalAuthenticationSuccessHandler globalAuthenticationSuccessHandler,
            GlobalAuthenticationFailureHandler globalAuthenticationFailureHandler
    ) {
        super(authenticationManager);
        this.objectMapper = objectMapper;
        super.setFilterProcessesUrl(AUTH_BASE_URI + LOGIN);
        super.setAuthenticationSuccessHandler(globalAuthenticationSuccessHandler);
        super.setAuthenticationFailureHandler(globalAuthenticationFailureHandler);
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) {
        UsernamePasswordAuthenticationToken authenticationToken = getAuthenticationToken(request);
        setDetails(request, authenticationToken);

        return getAuthenticationManager().authenticate(authenticationToken);
    }

    private UsernamePasswordAuthenticationToken getAuthenticationToken(
            HttpServletRequest httpRequest
    ) {
        try {
            LoginDto.Request request = objectMapper.readValue(httpRequest.getInputStream(), LoginDto.Request.class);
            return new UsernamePasswordAuthenticationToken(request.loginId(), request.password());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}

