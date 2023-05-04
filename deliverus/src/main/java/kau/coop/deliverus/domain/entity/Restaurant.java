package kau.coop.deliverus.domain.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@ToString
public class Restaurant {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long restaurantId;
    private String name;
    private String address;
    private String phoneNumber;
    private String category;
    private Double rating;
    private Double latitude;
    private Double longitude;
    private String restaurantIntro;

    @Embedded
    Menu menu;
}
