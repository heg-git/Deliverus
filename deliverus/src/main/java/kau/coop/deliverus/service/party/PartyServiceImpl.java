package kau.coop.deliverus.service.party;

import kau.coop.deliverus.domain.dto.request.PartyRequestDto;
import kau.coop.deliverus.domain.entity.Party;
import kau.coop.deliverus.domain.entity.PartyMember;
import kau.coop.deliverus.repository.party.PartyRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class PartyServiceImpl implements PartyService{

    private final PartyRepository partyRepository;

    @Override
    public void createParty(PartyRequestDto requestDto) {

        List<PartyMember> partyMembers = new ArrayList<>();

        PartyMember partyMember = PartyMember.builder()
                .nickname(requestDto.getHost())
                .order(requestDto.getOrder())
                .build();

        partyMembers.add(partyMember);

        Party party = Party.builder()
                .partyName(requestDto.getPartyName())
                .pickUpAddress(requestDto.getPickUpAddress())
                .host(requestDto.getHost())
                .restaurantId(requestDto.getRestaurantId())
                .memberNum(requestDto.getMemberNum())
                .latitude(requestDto.getLatitude())
                .longitude(requestDto.getLongitude())
                .expireTime(requestDto.getExpireTime())
                .partyMembers(partyMembers)
                .build();

        partyRepository.join(party, partyMember);

    }

    @Override
    public void getRoomExistenceByNickname(String nickname) throws Exception {
        if (partyRepository.findByNickname(nickname).isPresent()){
            throw new Exception("사용자가 참여한 방이 존재합니다!");
        }
    }
}
