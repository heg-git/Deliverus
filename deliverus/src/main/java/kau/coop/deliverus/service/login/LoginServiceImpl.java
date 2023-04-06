package kau.coop.deliverus.service.login;

import kau.coop.deliverus.repository.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LoginServiceImpl implements LoginService {

    private final MemberRepository memberRepository;
    @Override
    public void login(String userid, String passwd) throws Exception {
    }
}
