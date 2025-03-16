package kr.beeen.matrix.global.security.provider;

import kr.beeen.matrix.exception.BusinessException;
import kr.beeen.matrix.global.security.model.PrincipalDetails;
import kr.beeen.matrix.global.security.util.EncodeUtil;
import kr.beeen.matrix.member.entity.Member;
import kr.beeen.matrix.member.persistence.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import static kr.beeen.matrix.global.security.error.SecurityError.INVALID_CREDENTIAL;


@Component
@RequiredArgsConstructor
public class MatrixAuthenticationProvider implements AuthenticationProvider {

    private final MemberRepository memberRepository;

    @Override
    public Authentication authenticate(Authentication authentication) {
        String loginId = authentication.getPrincipal().toString();
        String password = authentication.getCredentials().toString();

        Member member = memberRepository.findByLoginId(loginId)
                .orElseThrow(() -> BusinessException.from(INVALID_CREDENTIAL));

        if (isInvalidPassword(password, member)) {
            throw BusinessException.from(INVALID_CREDENTIAL);
        }

        PrincipalDetails principal = new PrincipalDetails(member);
        return new UsernamePasswordAuthenticationToken(principal, password, principal.getAuthorities());
    }

    private boolean isInvalidPassword(
            String password,
            Member member
    ) {
        return !EncodeUtil.matches(password, member.getPassword());
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}

