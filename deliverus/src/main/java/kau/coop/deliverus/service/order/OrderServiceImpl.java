package kau.coop.deliverus.service.order;

import kau.coop.deliverus.domain.dto.response.OrderResultResponseDto;
import kau.coop.deliverus.domain.dto.response.PartyMemberResponseDto;
import kau.coop.deliverus.domain.entity.Party;
import kau.coop.deliverus.domain.entity.PartyMember;
import kau.coop.deliverus.domain.entity.Restaurant;
import kau.coop.deliverus.domain.model.PartyState;
import kau.coop.deliverus.repository.party.PartyRepository;
import kau.coop.deliverus.repository.restaurant.RestaurantRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final PartyRepository partyRepository;
    private final RestaurantRepository restaurantRepository;

    @Override
    public Long getPartyState(Long partyId) throws Exception {
        Optional<Party> party = partyRepository.findById(partyId);
        if(party.isEmpty()) {
            throw new NullPointerException(partyId + "를 가진 파티방이 없습니다");
        }
        return party.get().getState();
    }

    @Override
    public PartyMember payOrder(String nickname) throws Exception {
        Optional<PartyMember> partyMember = partyRepository.findByNickname(nickname);
        if(partyMember.isEmpty()) {
            throw new NullPointerException(nickname + "가 참가중인 party가 없습니다.");
        }
        PartyMember newPartyMember = partyMember.get();
        newPartyMember.setPayed(true);
        partyRepository.updateMemberPayed(newPartyMember);

        // 모든 참가자가 전부 payed라면 state를 변경합니다.
        Party party = partyMember.get().getParty();
        List<PartyMember> partyMembers = party.getPartyMembers();
        boolean allPayed = true;
        for(PartyMember member : partyMembers) {
            if(!member.isPayed()) {
                allPayed = false;
                break;
            }
        }
        if(allPayed) {
            partyRepository.updatePartyState(party.getPartyId(), PartyState.PAYMENT_COMPLETE.getState());
        }

        return newPartyMember;
    }

    @Override
    public Party deliverOrder(Long partyId) throws Exception {
        Optional<Party> party = partyRepository.findById(partyId);
        if(party.isEmpty()) {
            throw new NullPointerException(partyId + "를 가진 party가 없습니다.");
        }
        Optional<Party> newParty = partyRepository.updatePartyState(partyId, PartyState.PAYMENT_AWAIT.getState());
        if(newParty.isEmpty()) {
            throw new Exception(partyId + "번 파티의 state를 변경하는데 오류 생김");
        }
        return newParty.get();
    }

    @Override
    public OrderResultResponseDto getOrderResult(Long partyId) throws Exception {
        Optional<Party> party = partyRepository.findById(partyId);
        if(party.isEmpty()) {
            throw new NullPointerException(partyId + "를 가진 파티방이 없습니다.");
        }

        List<PartyMemberResponseDto> pmResponseDto = new ArrayList<>();

        for(PartyMember pm : party.get().getPartyMembers()){
            pmResponseDto.add(new PartyMemberResponseDto(pm.getPartyMemberId(), pm.getNickname(), pm.getOrder()));
        }

        Restaurant restaurant = restaurantRepository.findById(party.get().getRestaurantId());
        OrderResultResponseDto response = OrderResultResponseDto.builder()
                .longitude(party.get().getLongitude())
                .host(party.get().getHost())
                .memberNum(party.get().getMemberNum())
                .minOrderPrice(restaurant.getMinOrderPrice())
                .partyMembers(pmResponseDto)
                .pickUpAddress(party.get().getPickUpAddress())
                .partyName(party.get().getPartyName())
                .restaurantName(restaurant.getName())
                .latitude(party.get().getLatitude())
                .category(restaurant.getCategory())
                .deliverTime(party.get().getDeliverTime())
                .deliveryFee(restaurant.getDeliveryFee())
                .build();

        return response;
    }
}
