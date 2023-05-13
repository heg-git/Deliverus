package kau.coop.deliverus.service.restaurant;

import kau.coop.deliverus.domain.dto.response.RestaurantListResponseDto;
import kau.coop.deliverus.domain.dto.response.RestaurantInfoResponseDto;
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
    public RestaurantInfoResponseDto getRestaurantInfo(Long id) {
        Restaurant restaurantInfo = restaurantRepository.findById(id);
        if (restaurantInfo == null) {
            return null;
        }

        return RestaurantInfoResponseDto.builder()
                .name(restaurantInfo.getName())
                .menu(restaurantInfo.getMenu())
                .phoneNumber(restaurantInfo.getPhoneNumber())
                .address(restaurantInfo.getAddress())
                .rating(restaurantInfo.getRating())
                .category(restaurantInfo.getCategory())
                .intro(restaurantInfo.getRestaurantIntro())
                .latitude(restaurantInfo.getLatitude())
                .longitude(restaurantInfo.getLongitude())
                .build();
    }

    /**
     * 모든 식당 가게에 대한 리스트를 반환합니다.
     */
    @Override
    public List<RestaurantListResponseDto> getRestaurantList() {
        List<RestaurantListResponseDto> restaurantDto = new ArrayList<>();

        List<Restaurant> restaurants = restaurantRepository.findAll();

        for(Restaurant r : restaurants) {

            RestaurantListResponseDto restaurant = RestaurantListResponseDto.builder()
                    .restaurant_id(r.getRestaurantId())
                    .name(r.getName())
                    .rating(r.getRating())
                    .intro(r.getRestaurantIntro())
                    .category(r.getCategory())
                    .build();

            restaurantDto.add(restaurant);
        }
        return restaurantDto;
    }

    @Override
    public List<RestaurantInfoResponseDto> getAll() {
        List<RestaurantInfoResponseDto> result = new ArrayList<>();

        List<Restaurant> restaurants = restaurantRepository.findAll();

        for(Restaurant restaurant : restaurants) {
            RestaurantInfoResponseDto dto = RestaurantInfoResponseDto.builder()
                    .longitude(restaurant.getLongitude())
                    .latitude(restaurant.getLatitude())
                    .intro(restaurant.getRestaurantIntro())
                    .address(restaurant.getAddress())
                    .name(restaurant.getName())
                    .phoneNumber(restaurant.getPhoneNumber())
                    .rating(restaurant.getRating())
                    .menu(restaurant.getMenu())
                    .category(restaurant.getCategory())
                    .build();

            result.add(dto);
        }
        return result;
    }
}
