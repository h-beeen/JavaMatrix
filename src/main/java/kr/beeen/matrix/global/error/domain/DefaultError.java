package kr.beeen.matrix.global.error.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.*;

@Getter
@AllArgsConstructor
public enum DefaultError implements ErrorEntity<DefaultError> {

    MATRIX_XX400(BAD_REQUEST, "잘못된 요청입니다. 잠시 후 다시 시도하세요."),
    MATRIX_XX401(UNAUTHORIZED, "권한이 없는 요청입니다."),
    MATRIX_XX403(FORBIDDEN, "요청이 제한되었습니다."),
    MATRIX_XX404(NOT_FOUND, "해당 리소스를 찾을 수 없습니다."),
    MATRIX_XX405(METHOD_NOT_ALLOWED, "http 메소드가 잘못 요청되었습니다."),
    MATRIX_XX500(INTERNAL_SERVER_ERROR, "서버에서 요청 처리 간 에러가 발생했습니다.");

    private final HttpStatus httpStatus;
    private final String message;
}
