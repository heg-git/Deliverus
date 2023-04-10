package kau.coop.deliverus.controller.login;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import kau.coop.deliverus.domain.dto.LoginDto;
import kau.coop.deliverus.domain.entity.Member;
import kau.coop.deliverus.domain.model.MemberModel;
import kau.coop.deliverus.service.login.LoginService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

import static kau.coop.deliverus.domain.session.SessionConst.LOGIN_MEMBER;


@RestController
@RequiredArgsConstructor
@Slf4j
public class LoginController {

    private final LoginService loginService;

    //이건 테스트 코드. 세션 검증이 필요한 api에 대해서 이렇게 사용하면 됨.
    @GetMapping("/important")
    public String needLoginVerification(@SessionAttribute(name = LOGIN_MEMBER, required = false) MemberModel model, HttpServletResponse response) {
        if(model == null) {
            try {
                response.sendRedirect("/login");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            return "로그인 필요";
        }
        else {
            return "이미 로그인 되었어요";
        }
    }

    @PostMapping("/login")
    public ResponseEntity<String> loginProc(@RequestBody LoginDto loginDto, HttpServletRequest request, HttpServletResponse response) {
        ResponseCookie cookie = ResponseCookie.from("test", "123")
                .path("/")
                .sameSite("None")
                .httpOnly(false)
                .secure(true)
                .maxAge(3000)
                .build();

        response.addHeader("Set-Cookie", cookie.toString());
        try {

            Member member = loginService.login(loginDto.getUserid(), loginDto.getPasswd());
            HttpSession session = request.getSession(true);
            session.setAttribute(LOGIN_MEMBER, new MemberModel(member.getNickname()));
            log.info("새로운 세션 멤버 생성 : " + member.getUserid());
            return ResponseEntity.ok("success");

        } catch (Exception e) {
            return ResponseEntity.ok("failed");
        }
    }
}
