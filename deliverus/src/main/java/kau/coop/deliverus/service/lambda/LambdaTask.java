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
        Top5 top5 = new Top5("1등", "2등", "3등", "4등", "5등");
        repository.update(top5);

    }

}
