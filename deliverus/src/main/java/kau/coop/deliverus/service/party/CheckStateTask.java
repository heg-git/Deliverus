package kau.coop.deliverus.service.party;

import kau.coop.deliverus.domain.entity.Party;
import kau.coop.deliverus.domain.model.PartyState;
import kau.coop.deliverus.repository.party.PartyRepository;
import lombok.*;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@AllArgsConstructor
@Slf4j
@Getter
@Setter
public class CheckStateTask implements Runnable {

    private Party party;
    private final PartyService partyService;
    private final PartyRepository partyRepository;
    private final ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();

    @Override
    public void run() {

        Long state = partyRepository.findById(party.getPartyId()).get().getState();
        party.setState(state);
        if(party.getState().equals(PartyState.ORDER_AWAIT.getState())) {
            try {
                partyService.deleteParty(party.getPartyId());
                log.info(party.getPartyId().toString() + "번 삭제 되었다!");
            }catch(Exception ignored){
                log.info("task ignored");
            }
        }else{
            executorService.schedule(new DeleteTask(party.getPartyId(), partyService), 60L, TimeUnit.MINUTES);
            log.info(party.getPartyId() + "번 파티방은 " + 60L + "분 뒤에 삭제");
        }
    }
}
