package kau.coop.deliverus.service.lambda.lambdadto;

import lombok.Data;
import lombok.Getter;

import java.util.List;

@Data
public class DustApiResponseDto {

    private Response response;

    @Data
    public static class Response {
        private Body body;
        @Data
        public static class Body {
            private List<ApiData> items;
        }
    }
    @Data
    @Getter
    public static class ApiData {
        private String so2Grade;
        private String coFlag;
        private String khaiValue;
        private String so2Value;
        private String coValue;
        private String pm10Flag;
        private String o3Grade;
        private String pm10Value;
        private String khaiGrade;
        private String sidoName;
        private String no2Flag;
        private String no2Grade;
        private String o3Flag;
        private String so2Flag;
        private String dataTime;
        private String coGrade;
        private String no2Value;
        private String stationName;
        private String pm10Grade;
        private String o3Value;
    }
}
