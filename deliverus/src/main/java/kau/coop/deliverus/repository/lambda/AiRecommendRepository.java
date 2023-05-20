package kau.coop.deliverus.repository.lambda;

import kau.coop.deliverus.domain.entity.Top5;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface AiRecommendRepository {

    Optional<Top5> update(Top5 top5);
    Optional<Top5> getTop5();
}

