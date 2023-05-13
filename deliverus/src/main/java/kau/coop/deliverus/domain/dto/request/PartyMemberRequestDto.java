package kau.coop.deliverus.domain.dto.request;

import kau.coop.deliverus.domain.entity.Order;
import lombok.Getter;
import lombok.ToString;

import java.util.List;

@Getter
@ToString
public class PartyMemberRequestDto {

    private Long partyId;
    private String nickname;
    private List<Order> order;

}
