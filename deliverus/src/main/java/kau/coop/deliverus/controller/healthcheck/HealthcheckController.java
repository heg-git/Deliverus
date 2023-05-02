package kau.coop.deliverus.controller.healthcheck;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class HealthcheckController {

    @GetMapping("api/healthcheck")
    public String heathCheck() {
        return "good";
    }
}
