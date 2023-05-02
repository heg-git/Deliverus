package kau.coop.deliverus.service.restaurant;

import kau.coop.deliverus.domain.dto.request.MenuRequestDto;
import kau.coop.deliverus.domain.dto.response.FoodResponseDto;
import kau.coop.deliverus.domain.dto.request.RestaurantInfoRequestDto;
import kau.coop.deliverus.domain.dto.response.RestaurantListResponseDto;
import kau.coop.deliverus.domain.dto.response.RestaurantInfoResponseDto;

import java.util.List;

public interface RestaurantService {

    RestaurantInfoResponseDto getRestaurantInfo(Long id);

    List<RestaurantListResponseDto> getRestaurantList();

    FoodResponseDto getMenu(MenuRequestDto requestDto);

    void putRestaurant(RestaurantInfoResponseDto restaurantDto);
}
