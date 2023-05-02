package kau.coop.deliverus.domain.dto.response;

import kau.coop.deliverus.domain.entity.Menu;
import lombok.*;

@Builder
@Getter
@ToString
public class RestaurantInfoResponseDto {

    private String name;
    private String address;
    private String phoneNumber;
    private String category;
    private Double rating;
    private Double latitude;
    private Double longitude;
    private Menu menu;
}
