package kau.coop.deliverus.domain.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Builder
@Getter
@ToString
public class FoodResponseDto {

    private String foodName;
    private Long price;

}
