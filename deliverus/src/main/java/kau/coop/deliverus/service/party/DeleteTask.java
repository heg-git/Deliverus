package kau.coop.deliverus.service.party;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@AllArgsConstructor
@Slf4j
@Getter
public class DeleteTask implements Runnable{

    private Long partyId;
    private final PartyService partyService;

    @Override
    public void run() {
        try {
            partyService.deleteParty(partyId);
            log.info(partyId.toString() + "번 삭제 되었다!");
        }catch(Exception ignored){
            log.info("task ignored");
        }
    }
}
