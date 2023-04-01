package kau.coop.deliverus.service.deliveryagent;

import kau.coop.deliverus.domain.entity.Menu;
import kau.coop.deliverus.domain.entity.Order;
import kau.coop.deliverus.domain.entity.Restaurant;

import java.util.List;

public interface DeliveryAgentService {

    /**
     * 식당 이름 조회하는 메서드
     * @return
     */
    List<Restaurant> inquireRestaurantName();

    /**
     * 식당 메뉴 조회하는 메서드
     * @return
     */
    Menu inquireRestaurantMenu();

    /**
     * 주문 요청하는 메서드
     * @return
     */
    Order returnOrder();
}
