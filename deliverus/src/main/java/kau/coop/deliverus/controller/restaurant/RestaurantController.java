package kau.coop.deliverus.controller.restaurant;

import kau.coop.deliverus.domain.dto.request.RestaurantRequestDto;
import kau.coop.deliverus.domain.dto.response.RestaurantResponseDto;
import kau.coop.deliverus.service.restaurant.RestaurantService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
public class RestaurantController {

    private final RestaurantService restaurantService;

    /**
     * 가게 정보를 가져오는 api 입니다.
     */
    @GetMapping("/api/restaurant/all")
    public ResponseEntity<List<RestaurantResponseDto>> getAllRestaurants() {
        //request에 따라서 결과를 전달합니다.
        List<RestaurantResponseDto> results = restaurantService.getRestaurant();
        if(!results.isEmpty()) {
            return ResponseEntity.ok(results);
        }
        else {
            return new ResponseEntity<>(results, HttpStatus.NO_CONTENT);
        }
    }

    @GetMapping("api/restaurant/category")
    public ResponseEntity<List<RestaurantResponseDto>> getRestaurantsByCategory(@RequestParam(name = "category") String category, @RequestParam(name = "address") String address) {
        RestaurantRequestDto request = RestaurantRequestDto.builder()
                .category(category)
                .address(address)
                .build();
        List<RestaurantResponseDto> results = restaurantService.getRestaurant(request);
        if(!results.isEmpty()) {
            return ResponseEntity.ok(results);
        }
        else {
            return new ResponseEntity<>(results, HttpStatus.NO_CONTENT);
        }
    }

    /**
     * 가게에 대한 메뉴 정보를 가져오는 api 입니다.
     */
//    @GetMapping("/api/restaurant/menu")
//    public List<FoodResponseDto> getMenu(){
////        return restaurantService.getMenu(requestDto);
//        return null;
//    }

    /**
     * 임시로 가게 정보를 DB에 넣어주는 api입니다. 다른 방법으로 직접 db에 넣는 것이 좋을 것 같습니다.
     */
//    @PostMapping("/api/restaurant")
//    public RestaurantResponseDto putRestaurant(@RequestBody RestaurantResponseDto restaurant){
//        restaurantService.putRestaurant(restaurant);
//        log.info("restaurant = {}", restaurant.toString());
//        return restaurant;
//    }
}
