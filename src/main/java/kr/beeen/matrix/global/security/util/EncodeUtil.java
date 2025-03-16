package kr.beeen.matrix.global.security.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class EncodeUtil {

    private static final PasswordEncoder encoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();

    public static String encode(String rawString) {
        return encoder.encode(rawString);
    }

    public static boolean matches(String rawString, String encodedString) {
        return encoder.matches(rawString, encodedString);
    }
}
