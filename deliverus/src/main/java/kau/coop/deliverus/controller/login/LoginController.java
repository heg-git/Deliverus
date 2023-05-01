package kau.coop.deliverus.controller.login;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import kau.coop.deliverus.domain.dto.request.LoginRequestDto;
import kau.coop.deliverus.domain.dto.response.LoginResponseDto;
import kau.coop.deliverus.domain.entity.Member;
import kau.coop.deliverus.domain.model.MemberModel;
import kau.coop.deliverus.service.login.LoginService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import static kau.coop.deliverus.domain.session.SessionConst.LOGIN_MEMBER;


@RestController
@RequiredArgsConstructor
@Slf4j
public class LoginController {

    private final LoginService loginService;

    //이건 테스트 코드. 세션 검증이 필요한 api에 대해서 이렇게 사용하면 됨.
//    @GetMapping("/api/important")
//    public TestResponseDto needLoginVerification(@SessionAttribute(name = LOGIN_MEMBER, required = false) MemberModel model) {
//        if(model != null) {
//            return new TestResponseDto(true);
//        }
//        else {
//            return new TestResponseDto(false);
//        }
//    }

    @PostMapping("/api/member/login")
    public ResponseEntity<LoginResponseDto> loginProc(@RequestBody LoginRequestDto loginDto, HttpServletRequest request) {

        log.info("id : " + loginDto.getUserid() + "   pw : " + loginDto.getPasswd());

        try {
            Member member = loginService.login(loginDto.getUserid(), loginDto.getPasswd());
            HttpSession session = request.getSession(true);
            session.setAttribute(LOGIN_MEMBER, new MemberModel(member.getNickname()));
            log.info("새로운 세션 멤버 생성 : " + member.getUserid());
            return new ResponseEntity<>(
                    LoginResponseDto.builder()
                            .userId(member.getNickname())
                            .build(), HttpStatus.OK);

        } catch (Exception e) {
            return new ResponseEntity<>(
                    LoginResponseDto.builder()
                            .build(), HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/api/member/logout")
    public void logout(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if(session == null) {
            return;
        }
        MemberModel member = (MemberModel) session.getAttribute(LOGIN_MEMBER);
        if(member == null) {
            return;
        }
        log.info("로그아웃됨 : " + member.getNickname());
        request.getSession().invalidate();
    }
}
