package kau.coop.deliverus.repository.restaurant;

import jakarta.persistence.EntityManager;
import kau.coop.deliverus.domain.entity.Restaurant;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
@Slf4j
public class MariaRestaurantRepository implements RestaurantRepository{

    private final EntityManager em;

    @Override
    public List<Restaurant> getAll() {
        return em.createQuery("select r from Restaurant r", Restaurant.class)
                .getResultList();
    }

    @Override
    public void join(Restaurant restaurant) {
        em.persist(restaurant);
    }


}
