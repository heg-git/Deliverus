package kau.coop.deliverus.domain.dto;

import kau.coop.deliverus.domain.entity.Menu;
import lombok.*;

@Builder
@Getter
@ToString
public class RestaurantDto {

    private String name;
    private String address;
    private String phoneNumber;
    private String category;
    private Double rating;
    private Menu menu;

}
