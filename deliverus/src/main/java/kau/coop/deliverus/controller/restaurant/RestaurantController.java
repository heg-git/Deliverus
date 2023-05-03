package kau.coop.deliverus.controller.restaurant;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import kau.coop.deliverus.domain.dto.request.RestaurantInfoRequestDto;
import kau.coop.deliverus.domain.dto.request.RestaurantListRequestDto;
import kau.coop.deliverus.domain.dto.response.RestaurantInfoResponseDto;
import kau.coop.deliverus.domain.dto.response.RestaurantListResponseDto;
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
    public ResponseEntity<List<RestaurantListResponseDto>> getAllRestaurants() {
        //세션 검증
//        HttpSession session = request.getSession(false);
//        if(session == null) {
//            return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
//        }

        //request에 따라서 결과를 전달합니다.
        List<RestaurantListResponseDto> results = restaurantService.getRestaurantList();
        if(!results.isEmpty()) {
            return ResponseEntity.ok(results);
        }
        else {
            return new ResponseEntity<>(results, HttpStatus.NO_CONTENT);
        }
    }

    @PostMapping("/api/restaurant/list")
    public ResponseEntity<List<RestaurantListResponseDto>> getRestaurantList(@RequestBody RestaurantListRequestDto requestDto) {
//        //세션 검증
//        HttpSession session = request.getSession(false);
//        if(session == null) {
//            return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
//        }

        log.info("사용자 위도 : " + requestDto.getLatitude() + " / 경도 : " + requestDto.getLongitude());

        //request에 따라서 결과를 전달합니다.
        List<RestaurantListResponseDto> results = restaurantService.getRestaurantList();
        if(!results.isEmpty()) {
            return ResponseEntity.ok(results);
        }
        else {
            return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
        }
    }

    @PostMapping("/api/restaurant/info")
    public ResponseEntity<RestaurantInfoResponseDto>  getRestaurantInfo(@RequestBody RestaurantInfoRequestDto requestDto) {
//        //세션 검증
//        HttpSession session = request.getSession(false);
//        if(session == null) {
//            return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
//        }

        RestaurantInfoResponseDto result = restaurantService.getRestaurantInfo(requestDto.getRestaurant_id());
        if(result != null) {
            return ResponseEntity.ok(result);
        }
        return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
    }

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
