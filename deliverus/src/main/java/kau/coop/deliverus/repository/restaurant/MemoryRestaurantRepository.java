package kau.coop.deliverus.repository.restaurant;

import kau.coop.deliverus.domain.entity.Restaurant;

import java.util.List;

public class MemoryRestaurantRepository implements RestaurantRepository{

    @Override
    public List<Restaurant> findAll() {
        return null;
    }

    @Override
    public Restaurant findById(Long id) {
        return null;
    }

    @Override
    public void join(Restaurant restaurant) {

    }

}
