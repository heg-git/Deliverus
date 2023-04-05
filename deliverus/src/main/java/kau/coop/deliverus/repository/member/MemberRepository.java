package kau.coop.deliverus.repository.member;

import kau.coop.deliverus.domain.entity.Member;

import java.util.List;
import java.util.Optional;

public interface MemberRepository {

    Member join(Member member);
    Optional<Member> findById(Long id);
    Optional<Member> findByNickname(String nickname);
    Optional<Member> findByUserid(String userid);
    List<Member> findAll();
}
