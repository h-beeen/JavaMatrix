package kr.beeen.matrix.global.security.handler;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import kr.beeen.matrix.exception.BusinessException;
import kr.beeen.matrix.global.error.domain.DefaultError;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;


@Component
public class GlobalAuthenticationFailureHandler implements AuthenticationFailureHandler {

    @Override
    public void onAuthenticationFailure(
            HttpServletRequest request,
            HttpServletResponse response,
            AuthenticationException exception
    ) {
        Throwable cause = exception.getCause();
        if (cause instanceof BusinessException) {
            throw (BusinessException) cause;
        } else {
            throw BusinessException.from(DefaultError.MATRIX_XX403);
        }
    }
}
