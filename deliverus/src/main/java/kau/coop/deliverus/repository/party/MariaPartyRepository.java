package kau.coop.deliverus.repository.party;

import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.NonUniqueResultException;
import kau.coop.deliverus.domain.entity.Party;
import kau.coop.deliverus.domain.entity.PartyMember;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
@Slf4j
public class MariaPartyRepository implements PartyRepository{

    private final EntityManager em;

    @Override
    public List<Party> findAll() {
        return em.createQuery("select p from Party p", Party.class)
                .getResultList();
    }

    @Override
    public List<Party> findByRestaurant(Long restaurantId) {
        return em.createQuery("select p from Party p where p.restaurantId=:restaurantId", Party.class)
                .setParameter("restaurantId", restaurantId)
                .getResultList();
    }

    @Override
    public void join(Party party, PartyMember partyMember) {
        party.addPartyMember(partyMember);
        em.persist(party);
    }

    @Override
    public void memberJoin(PartyMember partyMember, Long partyId) {
        Party party = Party.builder().partyId(partyId).build();
        partyMember.setParty(party);
        em.persist(partyMember);
    }

    @Override
    public Optional<PartyMember> findByNickname(String nickname) {
        try {
            return Optional.of(em.createQuery("select pm from PartyMember pm where pm.nickname=:nickname", PartyMember.class)
                    .setParameter("nickname", nickname)
                    .getSingleResult());
        } catch(NoResultException | NonUniqueResultException e){
            return Optional.empty();
        }
    }

    @Override
    public void deleteMember(PartyMember partyMember) {
        em.remove(partyMember);
        log.info(partyMember.getNickname()+"은 삭제되었다!");
    }

    @Override
    public Optional<Party> findById(Long partyId) {
        Party party = em.find(Party.class, partyId);
        if(party != null) return Optional.of(party);
        else return Optional.empty();
    }

    @Override
    @Transactional
    public Optional<Party> delete(Long partyId) {
        Party party = em.find(Party.class, partyId);
        if(party != null) {
            em.remove(party);
            log.info("party 제거 완료!" +party.getPartyId().toString() );
            return Optional.of(party);
        }
        else {
            log.info("삭제할 것 없음");
            return Optional.empty();
        }
    }
}
