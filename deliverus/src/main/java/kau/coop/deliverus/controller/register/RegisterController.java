package kau.coop.deliverus.controller.register;

import kau.coop.deliverus.domain.dto.RegisterDto;
import kau.coop.deliverus.domain.entity.Member;
import kau.coop.deliverus.service.member.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class RegisterController {

    private final MemberService memberService;

    @GetMapping(value = "/member/register")
    public void getRegister() {
        System.out.println("회원가입 페이지");
    }


    @PostMapping(value = "/member/register")
//    public String postRegister(@RequestParam(name = "nickname") String nickname, @RequestParam(name = "id") String userid, @RequestParam(name = "passwd") String passwd) {
    public Member postRegister(@RequestBody RegisterDto register){
        try {
            Member member = memberService.register(register.getNickname(), register.getUserid(), register.getPasswd());
            return member;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }
}
