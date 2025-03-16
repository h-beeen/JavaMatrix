package kr.beeen.matrix.global.security.util;

import io.jsonwebtoken.Jwts;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;

@Getter
@Component
@NoArgsConstructor
public class JwtSecretKeyUtil {

    @Getter
    private static final SecretKey secretKey = Jwts.SIG.HS512.key().build();
}
