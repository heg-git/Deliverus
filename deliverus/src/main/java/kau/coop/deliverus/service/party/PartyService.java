package kau.coop.deliverus.service.party;

import kau.coop.deliverus.domain.dto.request.PartyRequestDto;

public interface PartyService {

    void createParty(PartyRequestDto requestDto);

    void getRoomExistenceByNickname(String nickname) throws Exception;

}
