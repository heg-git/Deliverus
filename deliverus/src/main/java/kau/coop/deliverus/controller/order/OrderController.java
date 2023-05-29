package kau.coop.deliverus.controller.order;

import kau.coop.deliverus.domain.dto.request.PartyMemberOrderDto;
import kau.coop.deliverus.domain.dto.response.OrderResultResponseDto;
import kau.coop.deliverus.domain.dto.response.PartyInfoResponseDto;
import kau.coop.deliverus.domain.entity.Order;
import kau.coop.deliverus.service.order.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping("api/order/deliverOrder")
    public ResponseEntity<PartyInfoResponseDto> deliverOrder(@RequestParam("id") Long partyId) {
        try {
            // order 접수 후 state 변경

            PartyInfoResponseDto response = new PartyInfoResponseDto();
            return new ResponseEntity<>(response, HttpStatus.OK);
        }catch (Exception e) {

            if(e instanceof NullPointerException) {
                return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
            }
            else {
                return new ResponseEntity<>(null, HttpStatus.NOT_ACCEPTABLE);
            }
        }
    }


    // 파티멤버 개인별 주문 접수
    @PostMapping("api/order/payment")
    public ResponseEntity<List<Order>> payment(@RequestParam("order") PartyMemberOrderDto dto){
        try {
            // 결제 진행 로직

            ArrayList<Order> orders = new ArrayList<>();

            return new ResponseEntity<>(orders, HttpStatus.OK);
        }catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.NOT_ACCEPTABLE);
        }
    }

    // 주문완료 후 정보 받아오기
    @GetMapping("api/order")
    public ResponseEntity<OrderResultResponseDto> getOrder(@RequestParam("id") Long partyId) {
        try {
            // party id로 된 파티방에 대해 주문 로직

            OrderResultResponseDto response = new OrderResultResponseDto();
            return new ResponseEntity<>(response, HttpStatus.OK);
        }catch (Exception e) {

            if(e instanceof NullPointerException) {
                return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
            }
            else {
                return new ResponseEntity<>(null, HttpStatus.NOT_ACCEPTABLE);
            }
        }
    }

}
