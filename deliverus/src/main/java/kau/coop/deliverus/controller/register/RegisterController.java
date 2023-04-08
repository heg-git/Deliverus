package kau.coop.deliverus.controller.register;

import kau.coop.deliverus.domain.dto.RegisterDto;
import kau.coop.deliverus.domain.entity.Member;
import kau.coop.deliverus.service.member.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Slf4j
public class RegisterController {

    private final MemberService memberService;

    @PostMapping(value = "/member/register")
    public RegisterDto postRegister(@RequestBody RegisterDto register){
        try {
            memberService.register(register.getNickname(), register.getUserid(), register.getPasswd());
            return register;
        } catch (Exception e) {
            log.info("회원가입 문제 발생 : " + e.getMessage());
            return null;
        }
    }
}
