package kau.coop.deliverus.controller.healthcheck;

import kau.coop.deliverus.service.lambda.ApiServiceImpl;
import kau.coop.deliverus.service.lambda.LambdaTask;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class HealthcheckController {

    private final LambdaTask lambdaTask;

    @GetMapping("api/healthcheck")
    public String heathCheck() {

        return "good";
    }
}
