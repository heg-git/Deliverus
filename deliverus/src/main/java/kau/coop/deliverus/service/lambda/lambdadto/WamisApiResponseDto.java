package kau.coop.deliverus.service.lambda.lambdadto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class WamisApiResponseDto {

    private List<Info> list;

    @Data
    public static class Info {

        private String ymdh;
        private String ta;
        private String hm;
        private String td;
        private String ps;
        private String ws;
        private String wd;
        private String sihr1;
        private String catot;
        private String sdtot;
        private String sshr1;
    }
}