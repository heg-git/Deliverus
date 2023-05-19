package kau.coop.deliverus.service.lambda.lambdadto;

import lombok.Data;

import java.util.List;

@Data
public class WamisDayApiResponseDto {
    private List<WamisDayApiResponseDto.Info> list;

    @Data
    public static class Info {

        private String ymd;
        private String taavg;
        private String tamin;
        private String tamax;
        private String wsavg;
        private String wsmax;
        private String wdmax;
        private String hmavg;
        private String hmmin;
        private String evs;
        private String evl;
        private String catotavg;
        private String psavg;
        private String psmax;
        private String psmin;
        private String sdmax;
        private String tdavg;
        private String siavg;
        private String ssavg;
    }
}
