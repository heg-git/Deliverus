package kau.coop.deliverus.domain.entity;

import jakarta.persistence.*;
import lombok.*;

@NoArgsConstructor
@Entity
@Getter
@Setter
@ToString
public class Top5 {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String top1;
    private String top2;
    private String top3;
    private String top4;
    private String top5;

    public Top5(String top1, String top2, String top3, String top4, String top5) {
        this.top1 = top1;
        this.top2 = top2;
        this.top3 = top3;
        this.top4 = top4;
        this.top5 = top5;

    }
}
