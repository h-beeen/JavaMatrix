package kr.beeen.matrix.global.security.error;

import kr.beeen.matrix.global.error.domain.ErrorEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.CONFLICT;
import static org.springframework.http.HttpStatus.UNAUTHORIZED;

@Getter
@AllArgsConstructor
public enum SecurityError implements ErrorEntity<SecurityError> {
    INVALID_JWT(UNAUTHORIZED, "유효하지 않은 토큰이 요청되었습니다."),
    EXPIRED_JWT(UNAUTHORIZED, "만료된 토큰이 요청되었습니다."),
    NULL_AUTHORIZATION(UNAUTHORIZED, "Authorization 정보가 없습니다."),
    UNSUPPORTED_JWT(UNAUTHORIZED, "지원하지 않는 토큰 형식입니다."),
    MALFORMED_JWT(UNAUTHORIZED, "잘못된 형식의 JWT입니다."),
    INVALID_SIGNATURE(UNAUTHORIZED, "JWT 서명이 유효하지 않습니다."),
    NULL_OR_EMPTY_TOKEN(UNAUTHORIZED, "토큰은 null이나 empty일 수 없습니다."),
    INVALID_CREDENTIAL(UNAUTHORIZED, "입력하신 정보가 일치하지 않습니다."),
    CAN_T_ACCESS_USER_PASSWORD(UNAUTHORIZED, "로그인 아이디나 비밀번호가 올바르지 않습니다."),
    INACTIVE_USER(UNAUTHORIZED, "비활성화된 사용자입니다."),
    DUPLICATED_USER(CONFLICT, "이미 존재하는 사용자입니다.");

    private final HttpStatus httpStatus;
    private final String message;
}
