package kau.coop.deliverus.repository.lambda;

import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.NonUniqueResultException;
import kau.coop.deliverus.domain.entity.Top5;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
@Slf4j
public class MariaAiRecommendRepository implements AiRecommendRepository {

    private final EntityManager em;

    @Override
    public Optional<Top5> update(Top5 top5) {
        // 아무 데이터도 없다면, 데이터를 추가합니다.
        Top5 oldTop5;
        try {
            oldTop5 = em.createQuery("select t from Top5  t", Top5.class)
                    .getSingleResult();

        }catch (NoResultException | NonUniqueResultException e) {
            em.persist(top5);
            log.info("새로운 top5 생성 : " + top5.toString());
            return Optional.of(top5);
        }

        //데이터가 있다면, 데이터를 update 합니다.
        oldTop5.setTop1(top5.getTop1());
        oldTop5.setTop2(top5.getTop2());
        oldTop5.setTop3(top5.getTop3());
        oldTop5.setTop4(top5.getTop4());
        oldTop5.setTop5(top5.getTop5());
        em.merge(oldTop5);
        log.info("top5 updated : " + oldTop5.toString());
        return Optional.of(top5);
    }

    @Override
    public Optional<Top5> getTop5() {
        try {
            return Optional.of(em.createQuery("select t from Top5 t", Top5.class)
                    .getSingleResult());
        } catch (NoResultException | NonUniqueResultException e) {
            return Optional.empty();
        }
    }
}
