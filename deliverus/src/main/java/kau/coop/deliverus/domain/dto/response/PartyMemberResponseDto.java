package kau.coop.deliverus.domain.dto.response;

import kau.coop.deliverus.domain.entity.Order;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class PartyMemberResponseDto {
    private Long partyMemberId;
    private String nickname;
    private List<Order> order;
}
