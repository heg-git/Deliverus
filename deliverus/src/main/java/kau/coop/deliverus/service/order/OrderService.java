package kau.coop.deliverus.service.order;

import kau.coop.deliverus.domain.dto.response.OrderResultResponseDto;
import kau.coop.deliverus.domain.entity.Party;
import kau.coop.deliverus.domain.entity.PartyMember;

public interface OrderService {


    Long getPartyState(Long partyId) throws Exception;

    PartyMember payOrder(String nickname) throws Exception;

    Party deliverOrder(Long partyId) throws Exception;
    OrderResultResponseDto getOrderResult(Long partyId) throws Exception;

}
