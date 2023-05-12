package kau.coop.deliverus.domain.dto.request;

import kau.coop.deliverus.domain.entity.Order;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
public class PartyRequestDto {

    private String partyName;
    private String pickUpAddress;
    private Long memberNum;
    private Double latitude;
    private Double longitude;
    private Long restaurantId;
    private Long expireTime;
    private String host;
    private List<Order> order;

}
