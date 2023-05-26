package kau.coop.deliverus.service.lambda.lambdadto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LambdaRequestDto {
    private String windSpeed;
    private String sunshine;
    private String humidity;
    private String cloudLevel;
    private String hour;
    private String season;
    private String weekday;
    private String isHoliday;
    private String feelingTemp;
    private String discomfortIndex;
    private String dustLevel;
    private String isRain;
}
