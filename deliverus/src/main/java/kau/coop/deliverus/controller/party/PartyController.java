package kau.coop.deliverus.controller.party;

import kau.coop.deliverus.domain.dto.request.PartyCreateRequestDto;
import kau.coop.deliverus.domain.dto.request.PartyLocationRequestDto;
import kau.coop.deliverus.domain.dto.request.PartyMemberRequestDto;
import kau.coop.deliverus.domain.dto.response.PartyListResponseDto;
import kau.coop.deliverus.service.party.PartyService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
public class PartyController {

    private final PartyService partyService;

    //파티방 생성 api
    @PostMapping("api/party")
    public ResponseEntity<Void> createParty(@RequestBody PartyCreateRequestDto requestDto) {
        //세션 검증
//        HttpSession session = request.getSession(false);
//        if(session == null) {
//            return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
//        }
        partyService.createParty(requestDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    //파티방 삭제 api 삭제 성공시 200 삭제할 파티방이 없는 경우 406
    @DeleteMapping("api/party/{id}")
    public ResponseEntity<String> deleteParty(@PathVariable("id") Long id){
        try {
            partyService.deleteParty(id);
            return new ResponseEntity<>("Delete success", HttpStatus.OK);
        }catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NO_CONTENT);
        }
    }

    //사용자의 위치에 따른 필터링된 파티방 정보들을 전송하는 api 성공시 200 정보가 없으면 204
    @PostMapping("api/party/location")
    public ResponseEntity<List<PartyListResponseDto>> getPartyList(@RequestBody PartyLocationRequestDto requestDto){
        List<PartyListResponseDto> results = partyService.getPartyListByLocation(requestDto);
        if (!results.isEmpty()) return new ResponseEntity<>(results, HttpStatus.OK);
        else return new ResponseEntity<>(results, HttpStatus.NO_CONTENT);
    }

    //파티방 멤버 추가 api 참여 성공시 200 방이 꽉 찼거나 방id 오류시 406
    @PostMapping("api/party/member")
    public ResponseEntity<String> postMember(@RequestBody PartyMemberRequestDto requestDto){
        try {
            partyService.participateParty(requestDto);
            return new ResponseEntity<>("Participate Success", HttpStatus.OK);
        } catch(Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_ACCEPTABLE);
        }
    }

    //파티방 멤버 삭제 api
    //@DeleteMapping("api/party/member/{nickname}")


    //사용자의 파티방 참여 여부를 알려주는 api 없으면 200, 있으면 406
    @GetMapping("api/party/validation")
    public ResponseEntity<String> validate(@RequestParam("name") String nickname){
        try {
            partyService.getPartyExistenceByNickname(nickname);
            return new ResponseEntity<>("Validated", HttpStatus.OK);
        } catch(Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_ACCEPTABLE);
        }
    }

}
