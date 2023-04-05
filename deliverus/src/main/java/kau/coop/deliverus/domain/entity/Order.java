package kau.coop.deliverus.domain.entity;

public class Order {

    private Long memberId;
    private Food food;

    public Order(Long memberId, Food food) {
        this.memberId = memberId;
        this.food = food;
    }
}
