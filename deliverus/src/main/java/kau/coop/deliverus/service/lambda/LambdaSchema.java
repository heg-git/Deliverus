package kau.coop.deliverus.service.lambda;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

// lambda 함수에 넣을 schema
// 변수명 변경하면 안됨!!
@Data
public class LambdaSchema {

    private String dayOfWeek; // 월화수목금토일 1234567
    private String isHoliday; // 공휴일 1, 아니면 0
    private String time; // (0 ~ 5), (6 ~ 11), (12 ~ 17), (18 ~ 23) -> 1 ~ 4
    private String season; // (3 ~ 5월), (6 ~ 8월), (9 ~ 11월), (12 ~ 2월) -> 1~4
    private String feelsLikeTmp; // 체감온도
    private String discomfortIdx; //불쾌 지수
    private String windSpeed; // 풍속
    private String sunshine; //일조시간
    private String cloud; //전운량
    private String humidity; // 습도
    private String rain; // 비 오면 1
    private String dust; // (30 이하 1), (31 ~ 80 : 2), (81 ~ 150 : 3), (151 이상 : 4) 미세먼지

}
