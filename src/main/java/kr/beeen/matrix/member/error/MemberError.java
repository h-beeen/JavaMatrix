package kr.beeen.matrix.member.error;

import kr.beeen.matrix.global.error.domain.ErrorEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum MemberError implements ErrorEntity<MemberError> {
    MEMBER_NOT_FOUND(HttpStatus.BAD_REQUEST, "해당 사용자를 찾을 수 없습니다.");

    private final HttpStatus httpStatus;
    private final String message;
}
