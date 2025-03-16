package kr.beeen.matrix.member.presentation;

import kr.beeen.matrix.global.dto.CommonResponse;
import kr.beeen.matrix.member.application.MemberService;
import kr.beeen.matrix.member.application.dto.CreateMember;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static kr.beeen.matrix.global.security.presentation.AuthUriConfig.AUTH_BASE_URI;
import static kr.beeen.matrix.global.security.presentation.AuthUriConfig.SIGNUP;

@RestController
@RequiredArgsConstructor
@RequestMapping(AUTH_BASE_URI)
public class MemberController {

    private final MemberService memberService;

    @PostMapping(SIGNUP)
    public CommonResponse createMember(@RequestBody CreateMember.Request request) {
        CreateMember.Response response = memberService.createMember(request);
        return CommonResponse.ok(response);
    }
}
