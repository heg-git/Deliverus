package kau.coop.deliverus.domain.dto.response;

import kau.coop.deliverus.domain.entity.Order;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderResultResponseDto {

    private Long deliverTime;
    private String partyName;
    private String host;
    private String pickUpAddress;
    private Long memberNum;
    private Double latitude;
    private Double longitude;
    private List<PartyMemberResponseDto> partyMembers;
    private String restaurantName;
    private String category;
    private Long deliveryFee;
    private Long minOrderPrice;
}
