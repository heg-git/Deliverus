package kau.coop.deliverus.domain.dto.request;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@Builder
@ToString
public class RestaurantRequestDto {

    private String category;
    private String address;
}
