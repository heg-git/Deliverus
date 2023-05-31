package kau.coop.deliverus.domain.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PartyInfoResponseDto {

    private String partyName;
    private String host;
    private String pickUpAddress;
    private Long memberNum;
    private Double latitude;
    private Double longitude;
    private List<PartyMemberResponseDto> partyMembers;
    private Long restaurantId;
    private String expireTime;
    private String restaurantName;
    private String category;
    private Long deliveryFee;
    private Long minOrderPrice;
}
