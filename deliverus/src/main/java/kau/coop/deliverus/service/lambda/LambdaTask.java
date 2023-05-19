package kau.coop.deliverus.service.lambda;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class LambdaTask {
    private final ApiService apiService;
    LambdaSchema lambdaSchema;

    @Scheduled(fixedDelay = 3600000) // 1시간마다 호출
    public void callLambda() {
        lambdaSchema = apiService.getLambdaSchema();
        log.info("api 호출 : " + lambdaSchema.toString());

        // call REST api (lambda function)
    }

}
