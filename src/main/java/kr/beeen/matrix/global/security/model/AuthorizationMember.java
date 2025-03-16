package kr.beeen.matrix.global.security.model;

import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

@Getter
public class AuthorizationMember extends User {

    private final Long memberId;

    public AuthorizationMember(
            Long userId,
            String username,
            Collection<? extends GrantedAuthority> authorities
    ) {
        super(username, null, authorities);
        this.memberId = userId;
    }
}
