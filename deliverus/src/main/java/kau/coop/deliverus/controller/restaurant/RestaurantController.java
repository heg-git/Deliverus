package kau.coop.deliverus.controller.restaurant;

import kau.coop.deliverus.domain.dto.RestaurantDto;
import kau.coop.deliverus.service.restaurant.RestaurantService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@CrossOrigin("*")
public class RestaurantController {

    private final RestaurantService restaurantService;

    @GetMapping("/restaurant")
    public List<RestaurantDto> getRestaurant(){
        return restaurantService.getRestaurant();
    }

    @PostMapping("/restaurant")
    public RestaurantDto putRestaurant(@RequestBody RestaurantDto restaurant){
        restaurantService.putRestaurant(restaurant);
        log.info("restaurant = {}", restaurant.toString());
        return restaurant;
    }


}
