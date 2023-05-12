package kau.coop.deliverus.repository.party;

import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.NonUniqueResultException;
import kau.coop.deliverus.domain.entity.Party;
import kau.coop.deliverus.domain.entity.PartyMember;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
@Slf4j
public class MariaPartyRepository implements PartyRepository{

    private final EntityManager em;

    @Override
    public List<Party> getALl(Long latitude, Long longitude) {
        return null;
    }

    @Override
    public void join(Party party, PartyMember partyMember) {
        party.addPartyMember(partyMember);
        em.persist(party);
    }

    @Override
    public Optional<PartyMember> findByNickname(String nickname) {
        try {
            return Optional.of(em.createQuery("select pm from PartyMember pm where pm.nickname=:nickname", PartyMember.class)
                    .setParameter("nickname", nickname)
                    .getSingleResult());
        } catch(NoResultException noResultException){
            return Optional.empty();
        }
    }

    @Override
    public void delete() {

    }
}
