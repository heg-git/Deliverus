package kau.coop.deliverus.domain.entity;

import jakarta.persistence.Embeddable;
import lombok.*;

@Embeddable
@NoArgsConstructor
@ToString
@Getter
public class Food {
    private String menuName;
    private Long price;
}
