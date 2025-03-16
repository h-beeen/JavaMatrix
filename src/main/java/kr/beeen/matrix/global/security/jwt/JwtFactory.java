package kr.beeen.matrix.global.security.jwt;


import io.jsonwebtoken.*;
import jakarta.servlet.http.HttpServletRequest;
import kr.beeen.matrix.exception.BusinessException;
import kr.beeen.matrix.global.security.model.AuthorizationMember;
import kr.beeen.matrix.global.security.util.JwtSecretKeyUtil;
import kr.beeen.matrix.member.entity.Member;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.User;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import static kr.beeen.matrix.global.security.error.SecurityError.*;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtFactory {

    private static final String AUTHORITY_KEY = "role";
    private static final int EXPIRY = 60 * 60;

    public AccessToken generateToken(Member member) {
        SecretKey secretKey = JwtSecretKeyUtil.getSecretKey();

        LocalDateTime expiryDateTime = LocalDateTime.now().plusSeconds(EXPIRY);
        Date expiration = Date.from(expiryDateTime.atZone(ZoneId.systemDefault()).toInstant());

        String token = Jwts.builder()
                .subject(member.getId().toString())
                .expiration(expiration)
                .claim(AUTHORITY_KEY, member.getAuthorization().name())
                .signWith(secretKey)
                .compact();

        return AccessToken.of(token, expiryDateTime);
    }

    public Claims getTokenClaims(String token) {
        try {
            SecretKey secretKey = JwtSecretKeyUtil.getSecretKey();

            return Jwts.parser()
                    .verifyWith(secretKey)
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();
        } catch (Exception exception) {
            switch (exception) {
                case ExpiredJwtException innerException -> throw BusinessException.of(EXPIRED_JWT, innerException);
                case UnsupportedJwtException innerException -> throw BusinessException.of(UNSUPPORTED_JWT, innerException);
                case MalformedJwtException innerException -> throw BusinessException.of(MALFORMED_JWT, innerException);
                case IllegalArgumentException innerException -> throw BusinessException.of(NULL_OR_EMPTY_TOKEN, innerException);
                default -> throw BusinessException.of(INVALID_JWT, exception);
            }
        }
    }


    public Authentication getAuthentication(
            HttpServletRequest request,
            String accessToken
    ) {
        Claims claims = getTokenClaims(accessToken);

        Long memberId = Long.parseLong(claims.getSubject());
        String role = claims.get(AUTHORITY_KEY).toString();

        Collection<GrantedAuthority> authorities = List.of(new SimpleGrantedAuthority(role));
        AuthorizationMember authorizationMember = new AuthorizationMember(memberId, memberId.toString(), authorities);

        return new UsernamePasswordAuthenticationToken(authorizationMember, null, authorizationMember.getAuthorities());
    }
}
