package kau.coop.deliverus.service.restaurant;

import kau.coop.deliverus.domain.dto.response.RestaurantListResponseDto;
import kau.coop.deliverus.domain.dto.response.RestaurantInfoResponseDto;

import java.util.List;

public interface RestaurantService {

    RestaurantInfoResponseDto getRestaurantInfo(Long id);

    List<RestaurantListResponseDto> getRestaurantList();

    List<RestaurantInfoResponseDto> getAll();
}
