package kau.coop.deliverus.service.party;

import kau.coop.deliverus.domain.dto.request.PartyCreateRequestDto;
import kau.coop.deliverus.domain.dto.request.PartyLocationRequestDto;
import kau.coop.deliverus.domain.dto.request.PartyMemberRequestDto;
import kau.coop.deliverus.domain.dto.response.PartyListResponseDto;
import kau.coop.deliverus.domain.entity.Party;
import kau.coop.deliverus.domain.entity.PartyMember;
import kau.coop.deliverus.domain.entity.Restaurant;
import kau.coop.deliverus.repository.party.PartyRepository;
import kau.coop.deliverus.repository.restaurant.RestaurantRepository;
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
    private final RestaurantRepository restaurantRepository;
    private static final Double R = 6371.0;

    @Override
    public void createParty(PartyCreateRequestDto requestDto) {

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
    public void deleteParty(Long id) throws Exception{
        if(partyRepository.delete(id).isEmpty()) throw new Exception("No content to delete");
    }

    @Override
    public void participateParty(PartyMemberRequestDto requestDto) throws Exception {

        PartyMember partyMember = PartyMember.builder()
                .nickname(requestDto.getNickname())
                .order(requestDto.getOrder())
                .build();

        try {
            partyRepository.memberJoin(partyMember, requestDto.getPartyId());
        }catch(Exception e) {
            throw new Exception("Party doesn't exist");
        }
    }

    @Override
    public void getPartyExistenceByNickname(String nickname) throws Exception {
        if(partyRepository.findByNickname(nickname).isPresent()) throw new Exception("Joined party already exist");
    }

    @Override
    public List<PartyListResponseDto> getPartyListByLocation(PartyLocationRequestDto requestDto) {
        List<PartyListResponseDto> responseDtoList = new ArrayList<>();
        List<Party> partyList = partyRepository.findAll();
        log.info("party 방의 개수: " + partyList.size());
        for(Party p : partyList) {
            Restaurant r = restaurantRepository.findById(p.getRestaurantId());
            double distance = haversine(requestDto.getLatitude(), requestDto.getLongitude(), p.getLatitude(), p.getLongitude()) * 1000;
            log.info("두 위치 사이의 거리는 " + distance);
            if (distance <= 1500.0) {
                responseDtoList.add(
                        PartyListResponseDto.builder()
                                .partyId(p.getPartyId())
                                .partyName(p.getPartyName())
                                .host(p.getHost())
                                .memberNum(p.getMemberNum())
                                .currentMemberNum((long)p.getPartyMembers().size())
                                .distance(distance)
                                .latitude(p.getLatitude())
                                .longitude(p.getLongitude())
                                .expireTime(p.getExpireTime())
                                .pickUpAddress(p.getPickUpAddress())
                                .restaurantName(r.getName())
                                .category(r.getCategory())
                                .deliveryFee(r.getDeliveryFee())
                                .build());
            }
        }
        log.info("남의 party 방의 개수: " + responseDtoList.size());
        return responseDtoList;
    }

    public Double haversine(Double lat1, Double lon1, Double lat2, Double lon2) {
        Double dLat = Math.toRadians(lat2 - lat1);
        Double dLon = Math.toRadians(lon2 - lon1);
        lat1 = Math.toRadians(lat1);
        lat2 = Math.toRadians(lat2);

        Double a = Math.sin(dLat / 2) * Math.sin(dLat / 2) + Math.sin(dLon / 2) * Math.sin(dLon / 2) * Math.cos(lat1) * Math.cos(lat2);
        Double c = 2 * Math.asin(Math.sqrt(a));
        return R * c;
    }

}
