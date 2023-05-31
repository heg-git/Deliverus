package kau.coop.deliverus.service.party;

import kau.coop.deliverus.domain.dto.request.*;
import kau.coop.deliverus.domain.dto.response.PartyInfoResponseDto;
import kau.coop.deliverus.domain.dto.response.PartyListResponseDto;
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
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class PartyServiceImpl implements PartyService{

    private final PartyRepository partyRepository;
    private final RestaurantRepository restaurantRepository;
    private final ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();

    private static final Double R = 6371.0;
    private static final Long DEFAULT_DELIVER_TIME = 30L;

    @Override
    public void createParty(PartyCreateRequestDto requestDto) throws Exception {
        if (partyRepository.findByNickname(requestDto.getHost()).isPresent()) throw new Exception("Joined party already exist");
        List<PartyMember> partyMembers = new ArrayList<>();

        PartyMember partyMember = PartyMember.builder()
                .nickname(requestDto.getHost())
                .order(requestDto.getOrder())
                .isPayed(false)
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
                .life(requestDto.getLife())
                .expireTime(requestDto.getExpireTime())
                .partyMembers(partyMembers)
                .state(PartyState.ORDER_AWAIT.getState())
                .deliverTime(DEFAULT_DELIVER_TIME)
                .build();

        partyRepository.join(party, partyMember);

        CheckStateTask checkStateTask = new CheckStateTask(party, this, partyRepository);
        executorService.schedule(checkStateTask, party.getLife(), TimeUnit.MINUTES);
    }


    @Override
    public void deleteParty(Long partyId) throws Exception{
        if(partyRepository.delete(partyId).isEmpty()) {
            throw new Exception("No content to delete");
        }
    }

    @Override
    public void participateParty(PartyMemberRequestDto requestDto) throws Exception {

        Optional<Party> partyOptional = partyRepository.findById(requestDto.getPartyId());
        if (partyOptional.isEmpty()) throw new Exception("Party doesn't exist");

        Party party = partyOptional.get();
        if (party.getPartyMembers().size() >= party.getMemberNum()) {
            log.info("party 꽉참");
            throw new Exception("Party is full");
        }

        Optional<PartyMember> partyMemberOptional = partyRepository.findByNickname(requestDto.getNickname());
        if (partyMemberOptional.isPresent() | requestDto.getNickname().equals(party.getHost())){
            log.info("파티에 이미 참여");
            throw new Exception("Joined party already exist");
        }

        PartyMember partyMember = PartyMember.builder()
                .nickname(requestDto.getNickname())
                .order(requestDto.getOrder())
                .isPayed(false)
                .build();
        partyRepository.memberJoin(partyMember, requestDto.getPartyId());

    }

    @Override
    public Party leaveParty(String nickname) throws Exception{
        Optional<PartyMember> partyMember = partyRepository.findByNickname(nickname);
        if(partyMember.isEmpty()) {
            log.info("해당 멤버 없음!");
            throw new Exception("Doesn't exist anywhere");
        }
        PartyMember pm = partyMember.get();
        partyRepository.deleteMember(pm);
        return pm.getParty();

    }

    @Override
    public Long getPartyExistenceByNickname(String nickname) throws Exception {
        Optional<PartyMember>  partyMember = partyRepository.findByNickname(nickname);
        if(partyMember.isEmpty()) throw new Exception();
        return partyMember.get().getParty().getPartyId();
    }

    @Override
    public PartyInfoResponseDto getPartyInfoById(Long partyId) throws Exception {

        Optional<Party> party = partyRepository.findById(partyId);
        if (party.isEmpty()) throw new Exception("No content");

        Party p = party.get();
        Restaurant r = restaurantRepository.findById(p.getRestaurantId());

        List<PartyMemberResponseDto> pmResponseDto = new ArrayList<>();

        for(PartyMember pm : p.getPartyMembers()){
            pmResponseDto.add(new PartyMemberResponseDto(pm.getPartyMemberId(), pm.getNickname(), pm.getOrder()));
        }
        return PartyInfoResponseDto.builder()
                .partyName(p.getPartyName())
                .host(p.getHost())
                .pickUpAddress(p.getPickUpAddress())
                .memberNum(p.getMemberNum())
                .latitude(p.getLatitude())
                .longitude(p.getLongitude())
                .partyMembers(pmResponseDto)
                .expireTime(p.getExpireTime())
                .restaurantId(r.getRestaurantId())
                .restaurantName(r.getName())
                .category(r.getCategory())
                .deliveryFee(r.getDeliveryFee())
                .minOrderPrice(r.getMinOrderPrice())
                .build();
    }

    @Override
    public List<PartyListResponseDto> getPartyListByRestaurant(PartyRestaurantRequestDto requestDto) throws Exception{
        List<PartyListResponseDto> responseDtoList = new ArrayList<>();
        List<Party> partyList = partyRepository.findByRestaurant(requestDto.getId());
        Restaurant r = restaurantRepository.findById(requestDto.getId());

        if(partyList.isEmpty()) throw new Exception("No Party");

        for(Party p : partyList) {
            double distance = haversine(requestDto.getLatitude(), requestDto.getLongitude(), p.getLatitude(), p.getLongitude()) * 1000;
            if (distance <= 1500.0 && p.getState().equals(PartyState.ORDER_AWAIT.getState())) {
                responseDtoList.add(
                        PartyListResponseDto.builder()
                                .partyId(p.getPartyId())
                                .partyName(p.getPartyName())
                                .host(p.getHost())
                                .pickUpAddress(p.getPickUpAddress())
                                .memberNum(p.getMemberNum())
                                .currentMemberNum((long) p.getPartyMembers().size())
                                .distance(distance)
                                .latitude(p.getLatitude())
                                .longitude(p.getLongitude())
                                .expireTime(p.getExpireTime())
                                .restaurantId(r.getRestaurantId())
                                .restaurantName(r.getName())
                                .category(r.getCategory())
                                .deliveryFee(r.getDeliveryFee())
                                .minOrderPrice(r.getMinOrderPrice())
                                .build()
                );
            }
        }
        return responseDtoList;
    }

    @Override
    public List<PartyListResponseDto> getPartyListByLocation(PartyListRequestDto requestDto) {
        List<PartyListResponseDto> responseDtoList = new ArrayList<>();
        List<Party> partyList = partyRepository.findAll();
        log.info("party 방의 개수: " + partyList.size());
        for(Party p : partyList) {
            Restaurant r = restaurantRepository.findById(p.getRestaurantId());
            double distance = haversine(requestDto.getLatitude(), requestDto.getLongitude(), p.getLatitude(), p.getLongitude()) * 1000;
            if (distance <= 1500.0 && p.getState().equals(PartyState.ORDER_AWAIT.getState())) {
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
                                .restaurantId(p.getRestaurantId())
                                .restaurantName(r.getName())
                                .category(r.getCategory())
                                .deliveryFee(r.getDeliveryFee())
                                .minOrderPrice(r.getMinOrderPrice())
                                .build());
            }
        }
        return responseDtoList;
    }

    @Override
    public void changeOrder(String nickname, PartyMemberOrderRequestDto requestDto) throws Exception {
        Optional<PartyMember> partyMemberOptional = partyRepository.findByNickname(nickname);
        if(partyMemberOptional.isEmpty()) throw new Exception("He or she is not a member");
        PartyMember partyMember = partyMemberOptional.get();
        partyMember.setOrder(requestDto.getOrder());
        partyRepository.memberJoin(partyMember, partyMember.getParty().getPartyId());
    }

    @Override
    public Long queryState(Long partyId) throws Exception {
        Optional<Party> party = partyRepository.findById(partyId);
        if (party.isEmpty()) {
            throw new Exception(party + " ID를 가진 파티가 앖습니다");
        }
        return party.get().getState();
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
