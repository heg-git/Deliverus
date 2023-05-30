package kau.coop.deliverus.domain.entity;

import jakarta.persistence.*;
import lombok.*;

@Embeddable
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Order {

    private String menuName;
    private Long price;
    private Long num;

}