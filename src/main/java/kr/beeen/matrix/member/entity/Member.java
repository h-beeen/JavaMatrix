package kr.beeen.matrix.member.entity;

import jakarta.persistence.*;
import kr.beeen.matrix.global.auditing.BaseTimeEntity;
import kr.beeen.matrix.member.entity.constants.MemberAuthorization;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLRestriction;

@Getter
@Entity
@NoArgsConstructor
@SQLRestriction("is_available = true")
public class Member extends BaseTimeEntity {

    @Id
    @Column(unique = true, nullable = false, name = "member_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String loginId;

    private String password;

    private boolean isAvailable;

    @Enumerated(EnumType.STRING)
    private MemberAuthorization authorization;

    public Member(
            String loginId,
            String password
    ) {
        this.loginId = loginId;
        this.password = password;
        this.isAvailable = true;
        this.authorization = MemberAuthorization.GENERAL;
    }
}
