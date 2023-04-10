package kau.coop.deliverus.service.restaurant;

import kau.coop.deliverus.domain.dto.RestaurantDto;
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

    @Override
    public List<RestaurantDto> getRestaurant() {
        List<Restaurant> restaurants = restaurantRepository.getAll();
        List<RestaurantDto> restaurantDto = new ArrayList<>();

        for(Restaurant r : restaurants) {

            RestaurantDto restaurant = RestaurantDto.builder()
                    .name(r.getName())
                    .address(r.getAddress())
                    .phoneNumber(r.getPhoneNumber())
                    .category(r.getCategory())
                    .rating(r.getRating())
                    .menu(r.getMenu())
                    .build();

            restaurantDto.add(restaurant);
        }

        return restaurantDto;
    }

    @Override
    public void putRestaurant(RestaurantDto restaurantDto) {

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
