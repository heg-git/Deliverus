package kau.coop.deliverus.repository.party;

import kau.coop.deliverus.domain.entity.Party;
import kau.coop.deliverus.domain.entity.PartyMember;

import java.util.List;
import java.util.Optional;


public interface PartyRepository {

    void join(Party party, PartyMember partyMember);

    void memberJoin(PartyMember partyMember, Long partyId);

    List<Party> findAll();

    List<Party> findByRestaurant(Long restaurantId);

    Optional<PartyMember> findByNickname(String nickname);

    void deleteMember(PartyMember partyMember);

    Optional<Party> findById(Long partyId);

    Optional<Party> delete(Long partyId);

    Optional<PartyMember> updateMemberPayed(PartyMember partyMember);
    Optional<Party> updatePartyState(Long partyId, Long state);

}
