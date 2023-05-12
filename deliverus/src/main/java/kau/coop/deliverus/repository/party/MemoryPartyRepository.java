package kau.coop.deliverus.repository.party;

import kau.coop.deliverus.domain.entity.Party;
import kau.coop.deliverus.domain.entity.PartyMember;

import java.util.List;
import java.util.Optional;

public class MemoryPartyRepository implements PartyRepository{
    @Override
    public List<Party> getALl(Long latitude, Long longitude) {
        return null;
    }

    @Override
    public void join(Party party, PartyMember partyMember) {

    }

    @Override
    public Optional<PartyMember> findByNickname(String nickname) {
        return Optional.empty();
    }

    @Override
    public void delete() {

    }
}
