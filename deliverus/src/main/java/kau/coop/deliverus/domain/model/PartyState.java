package kau.coop.deliverus.domain.model;

public enum PartyState {
    ORDER_AWAIT(0L), PAYMENT_AWAIT(1L),PAYMENT_COMPLETE (2L);
    private final Long state;

    PartyState(Long state) {
        this.state = state;
    }

    public Long getState() {
        return state;
    }
}
