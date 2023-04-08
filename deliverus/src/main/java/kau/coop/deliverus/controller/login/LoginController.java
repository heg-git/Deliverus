package kau.coop.deliverus.controller.login;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import kau.coop.deliverus.domain.dto.LoginDto;
import kau.coop.deliverus.domain.entity.Member;
import kau.coop.deliverus.service.login.LoginService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

import static kau.coop.deliverus.domain.session.SessionConst.LOGIN_MEMBER;


@RestController
@RequiredArgsConstructor
@Slf4j
@CrossOrigin("*")
public class LoginController {

    private final LoginService loginService;

    @GetMapping("/important")
    public String needLoginVerification(@SessionAttribute(name = LOGIN_MEMBER, required = false) Member member, HttpServletResponse response) {
        if(member == null) {
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
    public String loginProc(@RequestBody LoginDto loginDto, HttpServletRequest request) {
        try {
            Member member = loginService.login(loginDto.getUserid(), loginDto.getPasswd());
            HttpSession session = request.getSession(true);
            session.setAttribute(LOGIN_MEMBER, member);
            log.info("새로운 세션 멤버 생성 : " + member.getUserid());
            return "login 성공!!";

        } catch (Exception e) {
            return "login 실패..";
        }
    }
}
