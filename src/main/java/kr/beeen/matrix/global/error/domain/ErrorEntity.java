package kr.beeen.matrix.global.error.domain;

import org.springframework.http.HttpStatus;

public interface ErrorEntity<E extends Enum<E>> {
    HttpStatus getHttpStatus();
    String getMessage();
}
