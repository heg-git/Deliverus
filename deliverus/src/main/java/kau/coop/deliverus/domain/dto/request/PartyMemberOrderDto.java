package kau.coop.deliverus.domain.dto.request;

import kau.coop.deliverus.domain.entity.Order;
import lombok.Getter;

import java.util.List;

@Getter
public class PartyMemberOrderDto {

    private Long partyId;
    private String nickname;
}
