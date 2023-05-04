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
        Restaurant restaurantInfo = restaurantRepository.getOneById(id);
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
                .build();
    }

    /**
     * 모든 식당 가게에 대한 리스트를 반환합니다.
     */
    @Override
    public List<RestaurantListResponseDto> getRestaurantList() {
        List<RestaurantListResponseDto> restaurantDto = new ArrayList<>();

        List<Restaurant> restaurants = restaurantRepository.getAll();

        for(Restaurant r : restaurants) {

            RestaurantListResponseDto restaurant = RestaurantListResponseDto.builder()
                    .restaurant_id(r.getRestaurantId())
                    .name(r.getName())
                    .rating(r.getRating())
                    .build();

            restaurantDto.add(restaurant);
        }
        return restaurantDto;
    }
}
