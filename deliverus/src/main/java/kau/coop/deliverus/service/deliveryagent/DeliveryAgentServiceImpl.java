package kau.coop.deliverus.service.deliveryagent;

import kau.coop.deliverus.domain.entity.Menu;
import kau.coop.deliverus.domain.entity.Order;
import kau.coop.deliverus.domain.entity.Restaurant;
import kau.coop.deliverus.repository.order.OrderRepository;
import kau.coop.deliverus.repository.restaurant.RestaurantRepository;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class DeliveryAgentServiceImpl implements DeliveryAgentService{

    private final RestaurantRepository restaurantRepository;
    private final OrderRepository orderRepository;

    @Override
    public List<Restaurant> inquireRestaurantName() {
        return null;
    }

    @Override
    public Menu inquireRestaurantMenu() {
        return null;
    }

    @Override
    public Order returnOrder() {
        return null;
    }
}
