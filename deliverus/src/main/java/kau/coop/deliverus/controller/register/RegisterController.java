package kau.coop.deliverus.controller.register;

import kau.coop.deliverus.domain.dto.request.RegisterRequestDto;
import kau.coop.deliverus.domain.dto.response.RegisterResponseDto;
import kau.coop.deliverus.service.member.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Slf4j
public class RegisterController {

    private final MemberService memberService;

    @PostMapping(value = "/api/member/register")
    public ResponseEntity<RegisterResponseDto> postRegister(@RequestBody RegisterRequestDto register){
        try {
            memberService.register(register.getNickname(), register.getUserid(), register.getPasswd());
            return ResponseEntity.ok(RegisterResponseDto.builder()
                    .id(register.getUserid())
                    .nickname(register.getNickname())
                    .build());
        } catch (Exception e) {
            log.info("회원가입 문제 발생 : " + e.getMessage());
            return new ResponseEntity<>(RegisterResponseDto.builder()
                    .id(null)
                    .nickname(null)
                    .build(), HttpStatus.NOT_ACCEPTABLE);
        }
    }
}
