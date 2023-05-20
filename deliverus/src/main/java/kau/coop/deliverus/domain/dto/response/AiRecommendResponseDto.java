package kau.coop.deliverus.domain.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class AiRecommendResponseDto {

    private String top1;
    private String top2;
    private String top3;
    private String top4;
    private String top5;
}
