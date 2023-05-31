package kau.coop.deliverus.controller.party;

import kau.coop.deliverus.domain.dto.request.*;
import kau.coop.deliverus.domain.dto.response.PartyInfoResponseDto;
import kau.coop.deliverus.domain.dto.response.PartyListResponseDto;
import kau.coop.deliverus.domain.entity.Party;
import kau.coop.deliverus.service.party.PartyService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
public class PartyController {

    private final PartyService partyService;

    //파티방 생성 api 성공시 200 이미 생성한 멤버면 406
    @PostMapping("api/party")
    public ResponseEntity<String> createParty(@RequestBody PartyCreateRequestDto requestDto) {
        log.info(requestDto.toString());
        //세션 검증
//        HttpSession session = request.getSession(false);
//        if(session == null) {
//            return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
//        }
        try {
            partyService.createParty(requestDto);
            return new ResponseEntity<>("Create Success", HttpStatus.OK);
        }catch (Exception e) {
            log.info("이미 참가한 방 있음" + e.getMessage());
           return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_ACCEPTABLE);
        }
    }

    //파티방 삭제 api 삭제 성공시 200 삭제할 파티방이 없는 경우 204
    @DeleteMapping("api/party/{id}")
    public ResponseEntity<String> deleteParty(@PathVariable("id") Long partyId){
        try {
            partyService.deleteParty(partyId);
            return new ResponseEntity<>("Delete success", HttpStatus.OK);
        }catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NO_CONTENT);
        }
    }

    //파티방 정보 api 성공시 200 방 id 오류시 204
    @GetMapping("api/party")
    public ResponseEntity<PartyInfoResponseDto> getPartyInfo(@RequestParam("id") Long partyId){
        try {
            PartyInfoResponseDto responseDto = partyService.getPartyInfoById(partyId);
            return new ResponseEntity<>(responseDto, HttpStatus.OK);
        }catch (Exception e){
            log.info(e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
        }
    }

    //해당 식당으로 만들어진 파티방 리스트 없으면 204
    @PostMapping("api/party/restaurant")
    public ResponseEntity<List<PartyListResponseDto>> getPartyList(@RequestBody PartyRestaurantRequestDto requestDto){
        try {
            List<PartyListResponseDto> responseDto = partyService.getPartyListByRestaurant(requestDto);
            return new ResponseEntity<>(responseDto, HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(new ArrayList<>(), HttpStatus.NO_CONTENT);
        }
    }

    //사용자의 위치에 따른 필터링된 파티방 정보들을 전송하는 api 성공시 200 정보가 없으면 204
    @PostMapping("api/party/all")
    public ResponseEntity<List<PartyListResponseDto>> getPartyList(@RequestBody PartyListRequestDto requestDto){
        List<PartyListResponseDto> results = partyService.getPartyListByLocation(requestDto);
        if (!results.isEmpty()) return new ResponseEntity<>(results, HttpStatus.OK);
        else return new ResponseEntity<>(results, HttpStatus.NO_CONTENT);
    }

    //파티방 멤버 추가 api 참여 성공시 200 방이 꽉 찼거나 해당 파티방에 참여했거나 방id 오류시 406
    @PostMapping("api/party/member")
    public ResponseEntity<String> postMember(@RequestBody PartyMemberRequestDto requestDto){
        try {
            partyService.participateParty(requestDto);
            return new ResponseEntity<>("Participate Success", HttpStatus.OK);
        } catch(Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_ACCEPTABLE);
        }
    }

    //파티방 멤버 삭제 api 성공시 200 해당 멤버가 없으면 204
    @DeleteMapping("api/party/member/{name}")
    public ResponseEntity<String> deleteMember(@PathVariable("name") String nickname) {
        try {
            Party p = partyService.leaveParty(nickname);
            if(p.getPartyMembers().isEmpty() | nickname.equals(p.getHost())) partyService.deleteParty(p.getPartyId());
            return new ResponseEntity<>("Member delete success",HttpStatus.OK);
         } catch(Exception e) {
            log.info(e.getMessage());
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NO_CONTENT);
        }
    }

    //사용자의 파티방 참여 여부를 알려주는 api 상태코드 200, 참여한 방 있으면 해당 파티방 Id 없으면 -1L
    @GetMapping("api/party/id")
    public ResponseEntity<Long> validate(@RequestParam("name") String nickname){
        try {
            Long partyId = partyService.getPartyExistenceByNickname(nickname);
            return new ResponseEntity<>(partyId, HttpStatus.OK);
        } catch(Exception e) {
            return new ResponseEntity<>(-1L, HttpStatus.OK);
        }
    }

    //사용자가 파티방에서 메뉴 주문을 변경하는 api
    @PutMapping("api/party/order/{name}")
    public ResponseEntity<String> changeOrder(@PathVariable("name") String nickname, @RequestBody PartyMemberOrderRequestDto requestDto){
        log.info(requestDto.toString());
        try{
            partyService.changeOrder(nickname, requestDto);
            return new ResponseEntity<>("Change success!",HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NO_CONTENT);
        }
    }

    // party의 state를 질의하는 문제입니다.
    @GetMapping("api/party/state")
    public ResponseEntity<Long> queryState(@RequestParam("nickname") String nickname) {
        // nickname을 가진 파티원이 없다면 no content error
        Long partyId;
        try {
            partyId = partyService.getPartyExistenceByNickname(nickname);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
        }

        // nickname을 가진 파티원이 있다면 state를 가져옴
        Long state;
        try {
            state = partyService.queryState(partyId);
        } catch (Exception e) {
            // 방의 state가 설정되어 있지 않을 때 (치명적 오류)
            return new ResponseEntity<>(null, HttpStatus.NOT_ACCEPTABLE);
        }

        return new ResponseEntity<>(state, HttpStatus.OK);
    }


}
