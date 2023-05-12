package kau.coop.deliverus.domain.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Embeddable
@NoArgsConstructor
@ToString
@Getter
public class Menu {

    @ElementCollection
    @CollectionTable(
            name = "restaurantMenu",
            joinColumns = @JoinColumn(name = "restaurant_id")
    )
    private List<Food> menu;
}
