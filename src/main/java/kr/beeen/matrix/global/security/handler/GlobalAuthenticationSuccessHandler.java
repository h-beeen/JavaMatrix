package kr.beeen.matrix.global.security.handler;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import kr.beeen.matrix.global.dto.CommonResponse;
import kr.beeen.matrix.global.security.dto.LoginDto;
import kr.beeen.matrix.global.security.jwt.AccessToken;
import kr.beeen.matrix.global.security.jwt.JwtFactory;
import kr.beeen.matrix.global.security.model.PrincipalDetails;
import kr.beeen.matrix.member.entity.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.util.MimeTypeUtils;

import java.io.IOException;
import java.io.PrintWriter;

@Component
@RequiredArgsConstructor
public class GlobalAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    private final ObjectMapper objectMapper;
    private final JwtFactory jwtFactory;

    @Override
    public void onAuthenticationSuccess(
            HttpServletRequest request,
            HttpServletResponse response,
            Authentication authentication
    ) throws IOException {
        PrincipalDetails details = (PrincipalDetails) authentication.getPrincipal();
        response.setContentType(MimeTypeUtils.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding("UTF-8");

        writeResponseBody(response, getJsonResponse(details.member()));
    }

    private void writeResponseBody(
            HttpServletResponse response,
            String jsonResponse
    ) throws IOException {
        PrintWriter writer = response.getWriter();
        writer.write(jsonResponse);
    }

    private String getJsonResponse(Member member) throws IOException {
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

        AccessToken accessToken = jwtFactory.generateToken(member);
        LoginDto.Response response = new LoginDto.Response(accessToken);
        CommonResponse responseEntity = CommonResponse.ok(response);

        return objectMapper.writerWithDefaultPrettyPrinter()
                .writeValueAsString(responseEntity);
    }
}

