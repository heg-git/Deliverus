package kau.coop.deliverus.controller.healthcheck;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HealthcheckController {

    @GetMapping("api/healthcheck")
    public String heathCheck() {

        return "good";
    }
}
