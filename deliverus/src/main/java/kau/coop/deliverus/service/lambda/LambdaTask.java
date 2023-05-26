package kau.coop.deliverus.service.lambda;

import ch.qos.logback.core.joran.sanity.Pair;
import kau.coop.deliverus.domain.entity.Top5;
import kau.coop.deliverus.repository.lambda.AiRecommendRepository;
import kau.coop.deliverus.service.lambda.lambdadto.LambdaRequestDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

@Component
@RequiredArgsConstructor
@Slf4j
@Transactional
public class LambdaTask {
    private final ApiService apiService;
    private final AiRecommendRepository repository;
    LambdaSchema lambdaSchema;

    HashMap<String, String> map = new HashMap<>() {{
        put("snack\\", "분식");
        put("jokbo\\", "족발/보쌈");
        put("jjim\\", "찜탕");
        put("chicken\\", "치킨");
        put("cafe\\", "카페/디저트");
        put("pizza\\", "피자");
        put("korean\\", "한식");
        put("japanese\\", "돈까스/일식");
        put("fastfood\\", "패스트푸드");
        put("asian\\", "아시안/양식");
    }};

    @Scheduled(fixedDelay = 3600000) // 1시간마다 호출
    public void callLambda() {
        lambdaSchema = apiService.getLambdaSchema();
        log.info("api 호출 : " + lambdaSchema.toString());

        // call REST api (lambda function)
        //update DB
        // "한식", "분식", "치킨", "아시안/양식", "족발/보쌈", "돈까스/일식", "카페/디저트", "찜탕", "패스트푸드", "피자"
        repository.update(getLambda(lambdaSchema));
    }

    private Top5 getLambda(LambdaSchema lambdaSchema) {
        RestTemplate restTemplate = new RestTemplate();

        try {
            LambdaRequestDto lambdaRequestDto = LambdaRequestDto.builder()
                    .discomfortIndex(lambdaSchema.getDiscomfortIdx())
                    .humidity(lambdaSchema.getHumidity())
                    .isHoliday(lambdaSchema.getIsHoliday())
                    .hour(lambdaSchema.getTime())
                    .season(lambdaSchema.getSeason())
                    .sunshine(lambdaSchema.getSunshine())
                    .cloudLevel(lambdaSchema.getCloud())
                    .feelingTemp(lambdaSchema.getFeelsLikeTmp())
                    .isRain(lambdaSchema.getRain())
                    .weekday(lambdaSchema.getDayOfWeek())
                    .windSpeed(lambdaSchema.getWindSpeed())
                    .dustLevel(lambdaSchema.getDust())
                    .build();

            ResponseEntity<String> response = restTemplate.exchange(
                    "https://jpgagir7s8.execute-api.ap-northeast-2.amazonaws.com/beta/lambda",
                    HttpMethod.POST,
                    new HttpEntity<>(lambdaRequestDto),
                    String.class
            );

            String[] split = Objects.requireNonNull(response.getBody()).split("\"");
            ArrayList<String> category = new ArrayList<>();
            category.add(split[2]);
            category.add(split[4]);
            category.add(split[6]);
            category.add(split[8]);
            category.add(split[10]);
            log.info(category.toString());
            return new Top5(map.get(category.get(0)), map.get(category.get(1)), map.get(category.get(2)), map.get(category.get(3)), map.get(category.get(4)));
        }catch (Exception e){
            log.info("lambda 결과 parsing중 error : " + e.getMessage());
            return new Top5("한식", "분식", "치킨", "아시안/양식", "족발/보쌈"); //"한식", "분식", "치킨", "아시안/양식", "족발/보쌈", "돈까스/일식", "카페/디저트", "찜탕", "패스트푸드", "피자"
        }
    }
}
