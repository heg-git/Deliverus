package kau.coop.deliverus.repository.restaurant;

import kau.coop.deliverus.domain.entity.Restaurant;

import java.util.List;

public class MemoryRestaurantRepository implements RestaurantRepository{

    @Override
    public List<Restaurant> getAll() {
        return null;
    }

    @Override
    public Restaurant getOneById(Long id) {
        return null;
    }

    @Override
    public void join(Restaurant restaurant) {

    }

}
