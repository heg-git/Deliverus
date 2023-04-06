package kau.coop.deliverus.service.member;

import kau.coop.deliverus.domain.dto.RegisterDto;
import kau.coop.deliverus.domain.entity.Member;

import java.util.Optional;

public interface MemberService {

    RegisterDto register(String nickname, String userid, String passwd) throws Exception;

    Optional<RegisterDto> findOne(Long id);
}
