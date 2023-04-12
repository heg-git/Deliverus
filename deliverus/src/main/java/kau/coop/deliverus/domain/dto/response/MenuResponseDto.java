package kau.coop.deliverus.domain.dto.response;

import kau.coop.deliverus.domain.entity.Food;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.util.List;

@Builder
@Getter
@ToString
public class MenuResponseDto  {

    private List<Food> menu;
}
