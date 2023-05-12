package kau.coop.deliverus.controller.party;

import jakarta.validation.Valid;
import kau.coop.deliverus.domain.dto.request.PartyRequestDto;
import kau.coop.deliverus.service.party.PartyService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
public class PartyController {

    private final PartyService partyService;

    @PostMapping("api/party")
    public ResponseEntity<Void> createParty(@RequestBody PartyRequestDto requestDto) throws Exception {
        //세션 검증
//        HttpSession session = request.getSession(false);
//        if(session == null) {
//            return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
//        }

        partyService.createParty(requestDto);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("api/party/validation")
    public ResponseEntity<String> validate(@RequestParam("name") String nickname){
        try {
            partyService.getRoomExistenceByNickname(nickname);
            return new ResponseEntity<>("success", HttpStatus.OK);
        } catch(Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_ACCEPTABLE);
        }
    }

}
