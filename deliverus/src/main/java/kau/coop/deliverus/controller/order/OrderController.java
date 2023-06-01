package kau.coop.deliverus.controller.order;

import kau.coop.deliverus.domain.dto.request.DeliverOrderRequestDto;
import kau.coop.deliverus.domain.dto.request.PartyMemberOrderRequestDto;
import kau.coop.deliverus.domain.dto.request.PaymentRequestDto;
import kau.coop.deliverus.domain.dto.response.OrderResultResponseDto;
import kau.coop.deliverus.domain.dto.response.PartyInfoResponseDto;
import kau.coop.deliverus.domain.entity.Order;
import kau.coop.deliverus.domain.entity.PartyMember;
import kau.coop.deliverus.domain.model.PartyState;
import kau.coop.deliverus.service.order.OrderService;
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
public class OrderController {



    private final OrderService orderService;
    private final PartyService partyService;

    // 방장이 주문 시작하면 호출하는 함수
    @PostMapping("api/order/deliverOrder")
    public ResponseEntity<PartyInfoResponseDto> deliverOrder(@RequestBody DeliverOrderRequestDto requestDto) {
        try {
            // order 접수 후 state 변경
            if(!(orderService.getPartyState(requestDto.getPartyId()).equals(PartyState.ORDER_AWAIT.getState()))) {
                return new ResponseEntity<>(null, HttpStatus.NOT_ACCEPTABLE);
            }

            orderService.deliverOrder(requestDto.getPartyId());
            PartyInfoResponseDto response = partyService.getPartyInfoById(requestDto.getPartyId());

            return new ResponseEntity<>(response, HttpStatus.OK);
        }catch (Exception e) {
            e.printStackTrace();

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
    public ResponseEntity<List<Order>> payment(@RequestBody PaymentRequestDto dto){
        try {
            // 결제 진행 로직
            if(!(orderService.getPartyState(dto.getPartyId()).equals(PartyState.PAYMENT_AWAIT.getState()))) {
                // PAYMENT_AWAIT state가 아니라면, 오류를 출력합니다.
                return new ResponseEntity<>(null, HttpStatus.NOT_ACCEPTABLE);
            }
            PartyMember partyMember = orderService.payOrder(dto.getNickname());

            return new ResponseEntity<>(partyMember.getOrder(), HttpStatus.OK);
        }catch (Exception e) {
            if(e instanceof NullPointerException) {
                // partyId에 해당하는 사람이 없을 때
                return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
            }
            else {
                return new ResponseEntity<>(null, HttpStatus.NOT_ACCEPTABLE);
            }
        }
    }

    // 주문완료 후 정보 받아오기
    @GetMapping("api/order")
    public ResponseEntity<OrderResultResponseDto> getOrder(@RequestParam("id") Long partyId) {
        try {
            // party id로 된 파티방에 대해 주문 로직
            if(!orderService.getPartyState(partyId).equals(PartyState.PAYMENT_COMPLETE.getState())) {
                // PAYMENT_COMPLETE 상태가 아니라면, 오류를 출력합니다.
                return new ResponseEntity<>(null, HttpStatus.NOT_ACCEPTABLE);
            }

            OrderResultResponseDto response = orderService.getOrderResult(partyId);

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
