package kau.coop.deliverus.service.party;

import kau.coop.deliverus.domain.dto.request.PartyCreateRequestDto;
import kau.coop.deliverus.domain.dto.request.PartyLocationRequestDto;
import kau.coop.deliverus.domain.dto.request.PartyMemberRequestDto;
import kau.coop.deliverus.domain.dto.response.PartyListResponseDto;

import java.util.List;

public interface PartyService {

    void createParty(PartyCreateRequestDto requestDto);

    void deleteParty(Long id) throws Exception;

    void participateParty(PartyMemberRequestDto requestDto) throws Exception;

    void getPartyExistenceByNickname(String nickname) throws Exception;

    List<PartyListResponseDto> getPartyListByLocation(PartyLocationRequestDto requestDto);

}
