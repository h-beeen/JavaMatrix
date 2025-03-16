package kr.beeen.matrix.exception;

import kr.beeen.matrix.global.error.domain.ErrorEntity;
import lombok.Getter;

@Getter
public class BusinessException extends RuntimeException {

    private final ErrorEntity errorEntity;

    private BusinessException(ErrorEntity errorEntity) {
        this.errorEntity = errorEntity;
    }

    private BusinessException(ErrorEntity errorEntity, Exception exception) {
        super(exception);
        this.errorEntity = errorEntity;
    }

    public static BusinessException from(
            ErrorEntity errorEntity
    ) {
        return new BusinessException(errorEntity);
    }

    public static BusinessException of(
            ErrorEntity<?> errorEntity,
            Exception exception
    ) {
        return new BusinessException(errorEntity, exception);
    }
}
