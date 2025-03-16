package kr.beeen.matrix.global.dto;

import kr.beeen.matrix.global.error.domain.ErrorEntity;
import lombok.AccessLevel;
import lombok.Builder;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.Map;

@Builder(access = AccessLevel.PRIVATE)
public record CommonResponse(
        String errorCode,
        String message,
        String timestamp,
        Object data
) {

    public static CommonResponse getClientErrorResponse(ErrorEntity<?> errorEntity) {
        return CommonResponse.builder()
                .errorCode(errorEntity.toString())
                .message(errorEntity.getMessage())
                .timestamp(LocalDateTime.now().toString())
                .data(null)
                .build();
    }

    public static Map<String, Object> getDefaultErrorResponse(HttpStatus httpStatus) {
        return Map.ofEntries(
                Map.entry("errorCode", "MATRIX_XX" + httpStatus.value()),
                Map.entry("message", httpStatus.getReasonPhrase()),
                Map.entry("timestamp", LocalDateTime.now().toString()),
                Map.entry("data", httpStatus.series())
        );
    }
}
