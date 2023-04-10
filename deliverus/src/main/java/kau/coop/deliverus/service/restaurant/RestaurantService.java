package kau.coop.deliverus.service.restaurant;

import kau.coop.deliverus.domain.dto.RestaurantDto;

import java.util.List;

public interface RestaurantService {

    List<RestaurantDto> getRestaurant();

    void putRestaurant(RestaurantDto restaurantDto);
}
