package kau.coop.deliverus.domain.dto.request;

import kau.coop.deliverus.domain.entity.Order;
import lombok.Getter;

import java.util.List;

@Getter
public class PartyMemberOrderRequestDto {
    private List<Order> order;
}
