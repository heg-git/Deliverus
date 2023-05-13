package kau.coop.deliverus.repository.restaurant;

import kau.coop.deliverus.domain.entity.Restaurant;

import java.util.List;

public interface RestaurantRepository {

    List<Restaurant> findAll();

    Restaurant findById(Long id);

    void join(Restaurant restaurant);

}
