package kau.coop.deliverus.domain.entity;

public class Order {

    private Long memberId;
    private Menu menuName;

    public Order(Long memberId, Menu menuName) {
        this.memberId = memberId;
        this.menuName = menuName;
    }
}
