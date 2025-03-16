package kr.beeen.matrix.global.filter;


import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import kr.beeen.matrix.exception.BusinessException;
import kr.beeen.matrix.global.error.domain.DefaultError;
import kr.beeen.matrix.global.error.util.GlobalErrorResponseWriter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
@RequiredArgsConstructor
public class GlobalExceptionHandlingFilter extends OncePerRequestFilter {

    private final GlobalErrorResponseWriter errorResponseWriter;

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain chain
    ) {
        try {
            chain.doFilter(request, response);
        } catch (BusinessException handledException) {
            errorResponseWriter.writeErrorResponse(response, handledException.getErrorEntity());
        } catch (Exception unhandledException) {
            errorResponseWriter.writeErrorResponse(response, DefaultError.MATRIX_XX500);
        }
    }
}
