package kau.coop.deliverus.service.login;

import kau.coop.deliverus.domain.entity.Member;
import kau.coop.deliverus.repository.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LoginServiceImpl implements LoginService {

    private final MemberRepository memberRepository;
    @Override
    public Member login(String userid, String passwd) throws Exception {
        Member member = memberRepository.findByUserid(userid).orElseThrow(() -> new Exception("일치하는 계정 정보가 없습니다."));
        if (member.getPasswd().equals(passwd)) {
            return member;
        }
        else {
            throw new Exception("비밀번호가 일치하지 않습니다");
        }
    }
}
