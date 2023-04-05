package kau.coop.deliverus.service.member;

import kau.coop.deliverus.domain.entity.Member;

import java.util.Optional;

public interface MemberService {

    Member register(String nickname, String userid, String passwd) throws Exception;

    Optional<Member> findOne(Long id);
}
