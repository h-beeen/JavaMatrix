package kr.beeen.matrix.global.error.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletResponse;
import kr.beeen.matrix.exception.BusinessException;
import kr.beeen.matrix.global.dto.CommonResponse;
import kr.beeen.matrix.global.error.domain.DefaultError;
import kr.beeen.matrix.global.error.domain.ErrorEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class GlobalErrorResponseWriter {

    private final ObjectMapper objectMapper;

    public void writeErrorResponse(
            HttpServletResponse response,
            ErrorEntity<?> errorEntity
    ) {
        try {
            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
            response.setStatus(errorEntity.getHttpStatus().value());

            CommonResponse errorResponse = CommonResponse.getClientErrorResponse(errorEntity);
            objectMapper.writerWithDefaultPrettyPrinter().writeValue(response.getOutputStream(), errorResponse);
        } catch (IOException exception) {
            throw BusinessException.of(DefaultError.MATRIX_XX500, exception);
        }
    }
}
