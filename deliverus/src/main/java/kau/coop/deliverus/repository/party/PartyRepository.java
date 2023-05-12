package kau.coop.deliverus.repository.party;

import kau.coop.deliverus.domain.entity.Party;
import kau.coop.deliverus.domain.entity.PartyMember;

import java.util.List;
import java.util.Optional;


public interface PartyRepository {

    List<Party> getALl(Long latitude, Long longitude);

    void join(Party party, PartyMember partyMember);

    Optional<PartyMember> findByNickname(String nickname);

    void delete();

}
