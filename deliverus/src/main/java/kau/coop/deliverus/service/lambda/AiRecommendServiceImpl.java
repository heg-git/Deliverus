package kau.coop.deliverus.service.lambda;

import kau.coop.deliverus.domain.dto.response.AiRecommendResponseDto;
import kau.coop.deliverus.domain.entity.Top5;
import kau.coop.deliverus.repository.lambda.AiRecommendRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AiRecommendServiceImpl implements AiRecommendService {

    private final AiRecommendRepository repository;

    @Override
    public AiRecommendResponseDto getTop5() throws Exception{
        Optional<Top5> top5 = repository.getTop5();
        if(top5.isEmpty()) {
            throw new Exception("top5 doesn't exits");
        }
        return AiRecommendResponseDto.builder()
                .top1(top5.get().getTop1())
                .top2(top5.get().getTop2())
                .top3(top5.get().getTop3())
                .top4(top5.get().getTop4())
                .top5(top5.get().getTop5())
                .build();
    }
}
