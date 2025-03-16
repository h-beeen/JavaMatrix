package kr.beeen.matrix.global.dto;

import kr.beeen.matrix.global.error.domain.ErrorEntity;
import lombok.AccessLevel;
import lombok.Builder;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.Map;

@Builder(access = AccessLevel.PRIVATE)
public record CommonResponse(
        String code,
        String message,
        String timestamp,
        Object data
) {

    public static CommonResponse ok(Object data) {
        HttpStatus httpStatus = HttpStatus.OK;

        return CommonResponse.builder()
                .code(httpStatus.toString())
                .message(httpStatus.getReasonPhrase())
                .timestamp(LocalDateTime.now().toString())
                .data(data)
                .build();
    }

    public static CommonResponse getClientErrorResponse(ErrorEntity<?> errorEntity) {
        return CommonResponse.builder()
                .code(errorEntity.toString())
                .message(errorEntity.getMessage())
                .timestamp(LocalDateTime.now().toString())
                .data(null)
                .build();
    }

    public static Map<String, Object> getDefaultErrorResponse(HttpStatus httpStatus) {
        return Map.ofEntries(
                Map.entry("code", "MATRIX_XX" + httpStatus.value()),
                Map.entry("message", httpStatus.getReasonPhrase()),
                Map.entry("timestamp", LocalDateTime.now().toString()),
                Map.entry("data", httpStatus.series())
        );
    }
}
