package kau.coop.deliverus.service.member;

import kau.coop.deliverus.domain.entity.Member;
import kau.coop.deliverus.repository.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;

    @Override
    public Member register(String nickname, String userid, String passwd) throws Exception {
        return memberRepository.join(validateMember(nickname, userid, passwd));
    }

    @Override
    public Optional<Member> findOne(Long id) {
        return Optional.empty();
    }

    private Member validateMember(String nickname, String userid, String passwd) throws Exception {
        if(memberRepository.findByUserid(userid).isPresent()) {
            throw new Exception("이미 존재하는 아이디입니다");
        }
        if (memberRepository.findByNickname(nickname).isPresent()) {
            throw new Exception("사용 불가능한 닉네임입니다");
        }
        return new Member(nickname, userid, passwd);
    }
}
