package kau.coop.deliverus.service.lambda;

import kau.coop.deliverus.service.lambda.lambdadto.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


@Service
@Slf4j
public class ApiServiceImpl implements ApiService {


    private static final String DEFAULT_DAY_OF_WEEK = "1";
    private static final String DEFAULT_IS_HOLIDAY = "0";
    private static final String DEFAULT_TIME = "1";
    private static final String DEFAULT_SEASON = "1";
    private static final String DEFAULT_FEELS_LIKE_TMP = "25";
    private static final String DEFAULT_DISCOMFORT_IDX = "1";
    private static final String DEFAULT_WIND_SPEED = "1";
    private static final String DEFAULT_SUNSHINE = "1";
    private static final String DEFAULT_CLOUD = "0";
    private static final String DEFAULT_HUMIDITY = "50";
    private static final String DEFAULT_RAIN = "0";
    private static final String DEFAULT_DUST = "1";
    private String date;

    // openweathermap api
    private String weatherApiUrl;

    @Value("${java.key.openweather}")
    private String weatherKey; // 발급받은 API key

    // wamis api
    private String wamisUrl;
    private String wamisDayUrl;

    //dust api
    @Value("${java.key.dataportalkey}")
    private String dustApiKey; // 발급받은 API key
    private String dustApiUrl;

    // 공휴일 api
    private String holidayApiUrl;
    @Value("${java.key.dataportalkey}")
    private String holidayApiKey;

    private void initProperties() {
        LocalDate now = LocalDate.now(ZoneId.of("Asia/Seoul"));

        String month;
        if(now.getMonth().getValue() >= 10) {
            month = "" + now.getMonth().getValue();
        }
        else {
            month = "0" + now.getMonth().getValue();
        }
        String yesterday = now.getYear() + month + (now.getDayOfMonth() - 1);
        String region = "서울";
        String lat = "37.56";
        String lon = "126.97";
        String units = "metric";

        date = now.getYear() + month + now.getDayOfMonth();
        wamisUrl = "http://www.wamis.go.kr:8080/wamis/openapi/wkw/we_hrdata?obscd=10181108&startdt=" + date + "&enddt=" + date;
        wamisDayUrl = "http://www.wamis.go.kr:8080/wamis/openapi/wkw/we_dtdata?obscd=10181108&startdt=" + yesterday + "&enddt=" + yesterday;
        holidayApiUrl = "http://apis.data.go.kr/B090041/openapi/service/SpcdeInfoService/getRestDeInfo?ServiceKey=" + holidayApiKey + "&solYear=2023&solMonth=" + month;
        dustApiUrl = "http://apis.data.go.kr/B552584/ArpltnInforInqireSvc/getCtprvnRltmMesureDnsty?serviceKey=" + dustApiKey + "&returnType=json&sidoName=" + region + "&searchDate=" + now + "&numOfRows=" + 40;
        // url에 사용될 정보
        weatherApiUrl = "https://api.openweathermap.org/data/2.5/weather?lat=" + lat + "&lon=" + lon + "&units=" + units + "&appid=" + weatherKey;
    }

    public LambdaSchema getLambdaSchema() {
        LambdaSchema lambdaSchema = new LambdaSchema();

        try {

            initProperties();

            LocalDate now = LocalDate.now(ZoneId.of("Asia/Seoul"));
            LocalTime time = LocalTime.now(ZoneId.of("Asia/Seoul"));

            // api 결과
            ArrayList<String> wamisApi = getWamisApi(); //humidity, wind, cloud, discomfortIndex
            ArrayList<String> weatherApi = getWeatherApi(); // rain, feelsliketmp
            String dustApi = getDustApi();
            String wamisDayApi = getWamisDayApi();
            ArrayList<String> holidayApi = getHolidayApi();

            // 요일
            lambdaSchema.setDayOfWeek(Integer.toString(now.getDayOfWeek().getValue()));

            //공휴일
            String isHoliday = "0";
            if(holidayApi == null) {
                //default value
                isHoliday = "0";
            }
            else {
                for (String day : holidayApi) {
                    if (date.equals(day)) {
                        isHoliday = "1";
                        break;
                    }
                }
            }
            lambdaSchema.setIsHoliday(isHoliday);

            // 시간
            if (time.getHour() < 6) {
                lambdaSchema.setTime("1");
            } else if (time.getHour() < 12) {
                lambdaSchema.setTime("2");
            } else if (time.getHour() < 18) {
                lambdaSchema.setTime("3");
            } else {
                lambdaSchema.setTime("4");
            }

            //계절
            int month = now.getMonth().getValue();
            if (month >= 3 && month <= 5) {
                lambdaSchema.setSeason("1");
            } else if (month >= 6 && month <= 8) {
                lambdaSchema.setSeason("2");
            } else if (month >= 9 && month <= 11) {
                lambdaSchema.setSeason("3");
            } else {
                lambdaSchema.setSeason("4");
            }

            //체감온도
            if(weatherApi == null || weatherApi.size() < 1) {
                lambdaSchema.setFeelsLikeTmp("28");
            }
            else {
                lambdaSchema.setFeelsLikeTmp(weatherApi.get(1));
            }

            //불쾌지수
            if(wamisApi == null || wamisApi.size() < 4) {
                //default
                lambdaSchema.setDiscomfortIdx("1");
            }
            else {
                float idx = Float.parseFloat(wamisApi.get(3));
                if (idx < 68) {
                    lambdaSchema.setDiscomfortIdx("1");
                } else if (idx < 75) {
                    lambdaSchema.setDiscomfortIdx("2");
                } else if (idx < 80) {
                    lambdaSchema.setDiscomfortIdx("3");
                } else {
                    lambdaSchema.setDiscomfortIdx("4");
                }
            }

            if(wamisApi == null || wamisApi.size() < 4) {
                lambdaSchema.setWindSpeed("1.0");
                lambdaSchema.setCloud("1.0");
                lambdaSchema.setHumidity("50");
            }
            else {
                // 풍속
                lambdaSchema.setWindSpeed(wamisApi.get(1));

                //전운량
                lambdaSchema.setCloud(wamisApi.get(2));

                //습도
                lambdaSchema.setHumidity(wamisApi.get(0));

            }
            //일조시간
            lambdaSchema.setSunshine(Objects.requireNonNullElse(wamisDayApi, "1.0"));



            //강수
            if(weatherApi == null || weatherApi.isEmpty()) {
                // default
                lambdaSchema.setRain("0");
            }
            else {
                if (Float.parseFloat(weatherApi.get(0)) > 0.5f) {
                    lambdaSchema.setRain("1");
                } else {
                    lambdaSchema.setRain("0");
                }
            }

            //미세먼지
            if(dustApi == null) {
                lambdaSchema.setDust("1");
            }
            else {
                float dust = Float.parseFloat(dustApi);
                if (dust < 30.0f) {
                    lambdaSchema.setDust("1");
                } else if (dust < 80.0f) {
                    lambdaSchema.setDust("2");
                } else if (dust < 150.0f) {
                    lambdaSchema.setDust("3");
                } else {
                    lambdaSchema.setDust("4");
                }
            }

            // 마지막으로 null이 들어갔는지 확인하고, null이라면 해당 property에 default 값을 넣어줍니다.
            nullCheck(lambdaSchema);

            return lambdaSchema;
        } catch (Exception e) {
            log.info("api 오류 : " + e);
            log.info("default schema return..");
            lambdaSchema.setDayOfWeek(DEFAULT_DAY_OF_WEEK);
            lambdaSchema.setIsHoliday(DEFAULT_IS_HOLIDAY);
            lambdaSchema.setTime(DEFAULT_TIME);
            lambdaSchema.setSeason(DEFAULT_SEASON);
            lambdaSchema.setFeelsLikeTmp(DEFAULT_FEELS_LIKE_TMP);
            lambdaSchema.setDiscomfortIdx(DEFAULT_DISCOMFORT_IDX);
            lambdaSchema.setWindSpeed(DEFAULT_WIND_SPEED);
            lambdaSchema.setSunshine(DEFAULT_SUNSHINE);
            lambdaSchema.setCloud(DEFAULT_CLOUD);
            lambdaSchema.setHumidity(DEFAULT_HUMIDITY);
            lambdaSchema.setRain(DEFAULT_RAIN);
            lambdaSchema.setDust(DEFAULT_DUST);
            return lambdaSchema;
        }
    }

    private ArrayList<String> getWeatherApi() {
        StringBuilder urlBuilder = new StringBuilder(weatherApiUrl);
        try {
            RestTemplate restTemplate = new RestTemplate();
            ResponseEntity<WeatherApiResponseDto> response = restTemplate.getForEntity(urlBuilder.toString(), WeatherApiResponseDto.class);

            String rain;
            if(Objects.requireNonNull(response.getBody()).getRain() == null) {
                rain = "0";
            }
            else {
                rain = Float.toString(response.getBody().getRain().getRain1h());
            }
            String feelsLikeTmp = Float.toString(response.getBody().getMain().getFeels_like());

            ArrayList<String> result = new ArrayList<>();

            result.add(rain);
            result.add(feelsLikeTmp);

            return result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private ArrayList<String> getWamisApi() {
        StringBuilder urlBuilder = new StringBuilder(wamisUrl);
        try {
            RestTemplate restTemplate = new RestTemplate();
            ResponseEntity<WamisApiResponseDto> response = restTemplate.getForEntity(urlBuilder.toString(), WamisApiResponseDto.class);
            List<WamisApiResponseDto.Info> list = Objects.requireNonNull(response.getBody()).getList();
            WamisApiResponseDto.Info info = list.get(list.size() - 1);

            String humidity = info.getHm();
            String wind = info.getWs();
            String cloud = info.getCatot();
            String temperature = info.getTa();
            String discomfortIndex = getDiscomfortIndex(Double.parseDouble(temperature), Double.parseDouble(humidity));

            ArrayList<String> result = new ArrayList<>();

            result.add(humidity);
            result.add(wind);
            result.add(cloud);
            result.add(discomfortIndex);

            return result;
        } catch (Exception e) {
            log.info("get wamis api에서 error : " + e.getMessage());
        }
        return null;
    }

    private String getWamisDayApi() {
        StringBuilder urlBuilder = new StringBuilder(wamisDayUrl);
        try {
            RestTemplate restTemplate = new RestTemplate();
            ResponseEntity<WamisDayApiResponseDto> response = restTemplate.getForEntity(urlBuilder.toString(), WamisDayApiResponseDto.class);
            List<WamisDayApiResponseDto.Info> list = Objects.requireNonNull(response.getBody()).getList();
            WamisDayApiResponseDto.Info info = list.get(0);

            if(info.getSsavg().startsWith(".")) {
                return "0" + info.getSsavg();
            }
            return info.getSsavg();
        } catch (Exception e) {
            log.info("get wamis day api에서 error : " + e.getMessage());
        }
        return null;
    }

    private String getDustApi() {
        StringBuilder urlBuilder = new StringBuilder(dustApiUrl);
        try {
            RestTemplate restTemplate = new RestTemplate();
            DustApiResponseDto response = restTemplate.getForObject(urlBuilder.toString(), DustApiResponseDto.class);
            assert response != null;
            List<DustApiResponseDto.ApiData> items = response.getResponse().getBody().getItems();
            DustApiResponseDto.ApiData item = items.get(30);

            return item.getPm10Value();
        } catch (Exception e) {
            log.info("get dust api에서 error : " + e.getMessage());
        }
        return null;
    }

    private ArrayList<String> getHolidayApi() {
        StringBuilder urlBuilder = new StringBuilder(holidayApiUrl);
        try {
            RestTemplate restTemplate = new RestTemplate();
            HolidayApiResponseDto response = restTemplate.getForObject(urlBuilder.toString(), HolidayApiResponseDto.class);
            assert response != null;
            List<HolidayApiResponseDto.Response.Body.Items.Item> items = response.getResponse().getBody().getItems().getItem();

            ArrayList<String> result = new ArrayList<>();
            for (HolidayApiResponseDto.Response.Body.Items.Item item : items) {
                if(!item.getIsHoliday().equals("Y")) {
                    continue;
                }
                result.add(item.getLocdate());
            }
            return result;
        } catch (Exception e) {
            log.info("get holiday api에서 error : " + e.getMessage());
        }
        return null;
    }

    private String getDiscomfortIndex(Double temperature, Double humidity) {
        // 불쾌지수=1.8x기온–0.55x(1–습도)x(1.8x기온–26)+32
        return Double.toString(1.8 * temperature - 0.55 * (1 - humidity) * (1.8 * temperature - 26) + 32);
    }

    private void nullCheck(LambdaSchema ls) {
        if(ls.getDust() == null) ls.setDust(DEFAULT_DUST);
        if(ls.getHumidity() == null) ls.setHumidity(DEFAULT_HUMIDITY);
        if(ls.getCloud() == null) ls.setCloud(DEFAULT_CLOUD);
        if(ls.getSunshine() == null) ls.setSunshine(DEFAULT_SUNSHINE);
        if(ls.getTime() == null) ls.setTime(DEFAULT_TIME);
        if(ls.getSeason() == null) ls.setSeason(DEFAULT_SEASON);
        if(ls.getRain() == null) ls.setRain(DEFAULT_RAIN);
        if(ls.getFeelsLikeTmp() == null) ls.setFeelsLikeTmp(DEFAULT_FEELS_LIKE_TMP);
        if(ls.getDiscomfortIdx() == null) ls.setDiscomfortIdx(DEFAULT_DISCOMFORT_IDX);
        if(ls.getIsHoliday() == null) ls.setIsHoliday(DEFAULT_IS_HOLIDAY);
        if(ls.getDayOfWeek() == null) ls.setDayOfWeek(DEFAULT_DAY_OF_WEEK);
        if(ls.getWindSpeed() == null) ls.setWindSpeed(DEFAULT_WIND_SPEED);
    }
}
