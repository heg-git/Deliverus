package kau.coop.deliverus.repository.party;

import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
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
    public List<Party> findAll() {
        return em.createQuery("select p from Party p", Party.class)
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
        } catch(NoResultException noResultException){
            return Optional.empty();
        }
    }

    @Override
    public Optional<PartyMember> deleteMember(String nickname) {

        return Optional.empty();
    }

    @Override
    public Optional<Party> findById(Long partyId) {
        Party party = em.find(Party.class, partyId);
        if(party != null) return Optional.of(party);
        else return Optional.empty();
    }

    @Override
    public Optional<Party> delete(Long partyId) {
        Party party = em.find(Party.class, partyId);
        if(party != null) {
            em.remove(party);
            return Optional.of(party);
        }
        else return Optional.empty();
    }

}
