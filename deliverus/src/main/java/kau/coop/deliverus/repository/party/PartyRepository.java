package kau.coop.deliverus.repository.party;

import kau.coop.deliverus.domain.entity.Party;
import kau.coop.deliverus.domain.entity.PartyMember;

import java.util.List;
import java.util.Optional;


public interface PartyRepository {

    void join(Party party, PartyMember partyMember);

    void memberJoin(PartyMember partyMember, Long partyId);

    List<Party> findAll();

    Optional<PartyMember> findByNickname(String nickname);

    Optional<PartyMember> deleteMember(String nickname);

    Optional<Party> findById(Long partyId);

    Optional<Party> delete(Long partyId);

}
