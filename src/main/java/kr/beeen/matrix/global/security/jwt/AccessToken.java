package kr.beeen.matrix.global.security.jwt;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class AccessToken {

    private String token;
    private String expiredAt;

    public static AccessToken of(
            String token,
            LocalDateTime expiredAt
    ) {
        return new AccessToken(token, expiredAt.toString());
    }
}
