package kau.coop.deliverus.domain.dto.request;

import kau.coop.deliverus.domain.entity.Order;
import lombok.Getter;
import lombok.ToString;

import java.util.List;

@ToString
@Getter
public class PartyMemberOrderRequestDto {

    private List<Order> order;
}
