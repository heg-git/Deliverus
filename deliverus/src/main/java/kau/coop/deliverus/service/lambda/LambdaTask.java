package kau.coop.deliverus.service.lambda;

import kau.coop.deliverus.domain.entity.Top5;
import kau.coop.deliverus.repository.lambda.AiRecommendRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
@Slf4j
@Transactional
public class LambdaTask {
    private final ApiService apiService;
    private final AiRecommendRepository repository;
    LambdaSchema lambdaSchema;

    @Scheduled(fixedDelay = 3600000) // 1시간마다 호출
    public void callLambda() {
        lambdaSchema = apiService.getLambdaSchema();
        log.info("api 호출 : " + lambdaSchema.toString());

        // call REST api (lambda function)

        //update DB
        // "한식", "분식", "치킨", "아시안/양식", "족발/보쌈", "돈까스/일식", "카페/디저트", "찜탕", "패스트푸드", "피자"
        Top5 top5 = new Top5("한식", "분식", "치킨", "아시안/양식", "족발/보쌈");
        repository.update(top5);

    }

}
