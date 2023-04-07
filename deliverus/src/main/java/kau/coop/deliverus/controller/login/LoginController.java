package kau.coop.deliverus.controller.login;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import kau.coop.deliverus.domain.UserInfo;
import kau.coop.deliverus.domain.dto.LoginFormDto;
import kau.coop.deliverus.domain.session.SessionManager;
import kau.coop.deliverus.service.login.LoginService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;


@RestController
@RequiredArgsConstructor
@Slf4j
public class LoginController {

    private final LoginService loginService;
    private final SessionManager sessionManager;

    @GetMapping("/member/login")
    public String test(HttpServletRequest request, HttpServletResponse response) {
        Object session = sessionManager.getSession(request);
        if(session == null) {
            try {
                response.sendRedirect("/member/login/loginForm");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        else{
            // 세션 정보가 있다면
            log.info("login 되어있음");
        }
        return "hello";
    }

    @GetMapping("/member/login/loginForm")
    public String loginForm() {
        return "login form";
    }
    @PostMapping("/member/login/loginForm")
    public String loginProc(@RequestBody LoginFormDto login, BindingResult bindingResult, HttpServletResponse response) {
        try {
            loginService.login(login.getUserid(), login.getPasswd());
            UserInfo userInfo = new UserInfo("hhtboy", 24);
            sessionManager.createSession(userInfo, response);
            return "login success!";
        } catch (Exception e) {
            log.error(e.getMessage());
            bindingResult.reject(e.getMessage());
            return "login failed..";
        }
    }
}
