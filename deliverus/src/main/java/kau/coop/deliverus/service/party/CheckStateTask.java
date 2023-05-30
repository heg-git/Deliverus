package kau.coop.deliverus.service.party;

import kau.coop.deliverus.domain.model.PartyState;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@AllArgsConstructor
@Slf4j
@Getter
public class CheckStateTask implements Runnable {

    private Long state;


    @Override
    public void run() {

    }
}
