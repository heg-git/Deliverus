package kau.coop.deliverus.domain.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class RestaurantListResponseDto {

    private Long restaurant_id;
    private String name;
    private Double rating;
    private String intro;
    private String category;
}
