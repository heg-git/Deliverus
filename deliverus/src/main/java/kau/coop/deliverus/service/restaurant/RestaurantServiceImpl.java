package kau.coop.deliverus.service.restaurant;

import kau.coop.deliverus.domain.dto.request.MenuRequestDto;
import kau.coop.deliverus.domain.dto.response.MenuResponseDto;
import kau.coop.deliverus.domain.dto.request.RestaurantRequestDto;
import kau.coop.deliverus.domain.dto.response.RestaurantResponseDto;
import kau.coop.deliverus.domain.entity.Menu;
import kau.coop.deliverus.domain.entity.Restaurant;
import kau.coop.deliverus.repository.restaurant.RestaurantRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class RestaurantServiceImpl implements RestaurantService{

    private final RestaurantRepository restaurantRepository;

    /**
     * request에 맞는 식당 가게 리스트를 반환합니다.
     */
    @Override
    public List<RestaurantResponseDto> getRestaurant(RestaurantRequestDto requestDto) {

        List<RestaurantResponseDto> restaurantDto = new ArrayList<>();

        /* 여기부터 --------------------------------------------------------*/
        List<Restaurant> restaurants = restaurantRepository.getAll();
        /* 여기까지 수정 ------------------------------------------------------- */

        for(Restaurant r : restaurants) {

            RestaurantResponseDto restaurant = RestaurantResponseDto.builder()
                    .name(r.getName())
                    .address(r.getAddress())
                    .phoneNumber(r.getPhoneNumber())
                    .category(r.getCategory())
                    .rating(r.getRating())
                    .menu(r.getMenu())
                    .latitude(r.getLatitude())
                    .longitude(r.getLongitude())
                    .build();

            restaurantDto.add(restaurant);
        }

        return restaurantDto;
    }

    /**
     * 모든 식당 가게에 대한 리스트를 반환합니다.
     */
    @Override
    public List<RestaurantResponseDto> getRestaurant() {

        List<RestaurantResponseDto> restaurantDto = new ArrayList<>();

        List<Restaurant> restaurants = restaurantRepository.getAll();

        for(Restaurant r : restaurants) {

            RestaurantResponseDto restaurant = RestaurantResponseDto.builder()
                    .name(r.getName())
                    .address(r.getAddress())
                    .phoneNumber(r.getPhoneNumber())
                    .category(r.getCategory())
                    .rating(r.getRating())
                    .menu(r.getMenu())
                    .latitude(r.getLatitude())
                    .longitude(r.getLongitude())
                    .build();

            restaurantDto.add(restaurant);
        }
        return restaurantDto;
    }

    @Override
    public MenuResponseDto getMenu(MenuRequestDto requestDto) {
        return null;
    }

    @Override
    public void putRestaurant(RestaurantResponseDto restaurantDto) {

        Restaurant restaurant = Restaurant.builder()
                .name(restaurantDto.getName())
                .address(restaurantDto.getAddress())
                .phoneNumber(restaurantDto.getPhoneNumber())
                .category(restaurantDto.getCategory())
                .rating(restaurantDto.getRating())
                .menu(restaurantDto.getMenu())
                .build();

        restaurantRepository.join(restaurant);
    }


}
