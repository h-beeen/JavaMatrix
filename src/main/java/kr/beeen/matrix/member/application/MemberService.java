package kr.beeen.matrix.member.application;

import kr.beeen.matrix.global.security.util.EncodeUtil;
import kr.beeen.matrix.member.application.dto.CreateMember;
import kr.beeen.matrix.member.entity.Member;
import kr.beeen.matrix.member.persistence.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    public CreateMember.Response createMember(CreateMember.Request request) {
        String loginId = request.loginId();
        String password = request.password();
        String encodedPassword = EncodeUtil.encode(password);

        Member member = new Member(loginId, encodedPassword);
        Member savedMember = memberRepository.save(member);

        return new CreateMember.Response(savedMember.getId());
    }
}
