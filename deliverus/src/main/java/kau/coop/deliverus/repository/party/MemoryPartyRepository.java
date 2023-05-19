package kau.coop.deliverus.repository.party;

import kau.coop.deliverus.domain.entity.Party;
import kau.coop.deliverus.domain.entity.PartyMember;

import java.util.List;
import java.util.Optional;

public class MemoryPartyRepository implements PartyRepository{

    @Override
    public List<Party> findAll() {
        return null;
    }

    @Override
    public List<Party> findByRestaurant(Long restaurantId) {
        return null;
    }

    @Override
    public void join(Party party, PartyMember partyMember) {

    }

    @Override
    public void memberJoin(PartyMember partyMember, Long partyId) {

    }

    @Override
    public Optional<PartyMember> findByNickname(String nickname) {
        return Optional.empty();
    }

    @Override
    public void deleteMember(PartyMember partyMember) {

    }

    @Override
    public Optional<Party> findById(Long partyId) {
        return Optional.empty();
    }

    @Override
    public Optional<Party> delete(Long partyId) {
        return Optional.empty();
    }

}
