package kau.coop.deliverus.service.party;

import kau.coop.deliverus.domain.dto.request.PartyCreateRequestDto;
import kau.coop.deliverus.domain.dto.request.PartyInfoRequestDto;
import kau.coop.deliverus.domain.dto.request.PartyListRequestDto;
import kau.coop.deliverus.domain.dto.request.PartyMemberRequestDto;
import kau.coop.deliverus.domain.dto.response.PartyInfoResponseDto;
import kau.coop.deliverus.domain.dto.response.PartyListResponseDto;

import java.util.List;

public interface PartyService {

    void createParty(PartyCreateRequestDto requestDto);

    void deleteParty(Long partyId) throws Exception;

    void participateParty(PartyMemberRequestDto requestDto) throws Exception;

    void getPartyExistenceByNickname(String nickname) throws Exception;

    PartyInfoResponseDto getPartyInfoById(Long partyId) throws Exception;

    List<PartyListResponseDto> getPartyListByLocation(PartyListRequestDto requestDto);

}
