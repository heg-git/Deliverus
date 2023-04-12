package kau.coop.deliverus.domain.dto.response;

import kau.coop.deliverus.domain.entity.Menu;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Builder
@Getter
@ToString
public class RestaurantResponseDto  {

    private String name;
    private String address;
    private String phoneNumber;
    private String category;
    private Double rating;
    private Menu menu;
}
