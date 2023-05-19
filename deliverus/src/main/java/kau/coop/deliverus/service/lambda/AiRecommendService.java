package kau.coop.deliverus.service.lambda;

import kau.coop.deliverus.domain.dto.response.AiRecommendResponseDto;

public interface AiRecommendService {

    AiRecommendResponseDto getTop5() throws Exception;

}
