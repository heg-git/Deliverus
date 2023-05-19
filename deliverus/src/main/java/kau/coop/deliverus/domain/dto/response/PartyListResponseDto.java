package kau.coop.deliverus.domain.dto.response;

import lombok.*;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PartyListResponseDto {

    private Long partyId;
    private String partyName;
    private String host;
    private String pickUpAddress;
    private Long memberNum;
    private Long currentMemberNum;
    private Double distance;
    private Double latitude;
    private Double longitude;
    private Long expireTime;
    private Long restaurantId;
    //restaurantId 추가
    private String restaurantName;
    private String category;
    private Long deliveryFee;
    //private Long minOrderPrice;

}
