package kau.coop.deliverus.service.restaurant;

import kau.coop.deliverus.domain.dto.request.MenuRequestDto;
import kau.coop.deliverus.domain.dto.response.FoodResponseDto;
import kau.coop.deliverus.domain.dto.request.RestaurantRequestDto;
import kau.coop.deliverus.domain.dto.response.RestaurantResponseDto;

import java.util.List;

public interface RestaurantService {

    List<RestaurantResponseDto> getRestaurant(RestaurantRequestDto requestDto);

    List<RestaurantResponseDto> getRestaurant();

    FoodResponseDto getMenu(MenuRequestDto requestDto);

    void putRestaurant(RestaurantResponseDto restaurantDto);
}
