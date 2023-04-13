package kau.coop.deliverus.repository.restaurant;

import kau.coop.deliverus.domain.entity.Restaurant;

import java.util.List;

public interface RestaurantRepository {

    List<Restaurant> getAll();

    void join(Restaurant restaurant);

}
