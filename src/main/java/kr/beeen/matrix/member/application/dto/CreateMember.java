package kr.beeen.matrix.member.application.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.validation.annotation.Validated;

@Validated
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CreateMember {

    public record Request(
            @Size(min = 1, max = 50, message = "로그인 아이디는 최대 50자입니다.")
            @NotNull(message = "로그인 아이디가 유효하지 않습니다.")
            String loginId,

            @Pattern(
                    regexp = "(?=.*[0-9])(?=.*[a-zA-Z])(?=.*\\W)(?=\\S+$).*",
                    message = "비밀번호는 영문, 숫자, 특수문자를 모두 포함해야 합니다.")
            @NotNull(message = "비밀번호가 유효하지 않습니다.")
            @Size(min = 1, max = 50, message = "비밀번호는 최대 50자입니다.")
            String password
    ) {
    }

    public record Response(
            Long userId
    ) {
    }
}
