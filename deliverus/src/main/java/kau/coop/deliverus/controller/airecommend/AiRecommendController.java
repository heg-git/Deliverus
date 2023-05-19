package kau.coop.deliverus.controller.airecommend;

import kau.coop.deliverus.domain.dto.response.AiRecommendResponseDto;
import kau.coop.deliverus.service.lambda.AiRecommendService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
public class AiRecommendController {

    private final AiRecommendService aiRecommendService;

    @GetMapping("/api/recommend")
    public ResponseEntity<AiRecommendResponseDto> getTop5() {
        try {
            return new ResponseEntity<>(aiRecommendService.getTop5(), HttpStatus.OK);
        } catch (Exception e) {
            log.info("error on recommend api : " + e);
            return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
        }
    }
}
