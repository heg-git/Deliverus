package kau.coop.deliverus.service.lambda;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class CurTimeInfo {

    private String weekday;
    private Boolean isHoliday;
    private String time;
    private String season;
}
