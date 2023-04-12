package kau.coop.deliverus.service.member;

import kau.coop.deliverus.domain.dto.request.RegisterRequestDto;

import java.util.Optional;

public interface MemberService {

    RegisterRequestDto register(String nickname, String userid, String passwd) throws Exception;

    Optional<RegisterRequestDto> findOne(Long id);
}
