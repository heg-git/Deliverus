package kau.coop.deliverus.domain.dto.request;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class PartyRestaurantRequestDto {

    private Long id;
    private Double latitude;
    private Double longitude;

}
