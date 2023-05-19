package kau.coop.deliverus.service.lambda.lambdadto;

import lombok.Data;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

@Data
public class HolidayApiResponseDto {
    private Response response;

    @Data
    public static class Response {
        private Body body;

        @Data
        public static class Body {
            private Items items;
            @Data
            public static class Items {
                private List<Item> item;

                @Data
                public static class Item {
                    private String locdate;
                    private String seq;
                    private String dateKind;
                    private String isHoliday;
                    private String dateName;
                    private String numOfRows;
                    private String pageNo;
                    private String totalCount;
                }

            }
        }
    }
}

