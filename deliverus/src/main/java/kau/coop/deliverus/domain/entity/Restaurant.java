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
    Long restaurant_id;
    String name;
    String address;
    String phoneNumber;
    String category;
    Double rating;
    Double latitude;
    Double longitude;

    @Embedded
    Menu menu;
}
