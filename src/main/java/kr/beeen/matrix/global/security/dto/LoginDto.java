package kr.beeen.matrix.global.security.dto;

import kr.beeen.matrix.global.security.jwt.AccessToken;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class LoginDto {

    public record Request(
            String loginId,
            String password
    ) {
    }

    public record Response(
            AccessToken accessToken
    ) {
    }
}
