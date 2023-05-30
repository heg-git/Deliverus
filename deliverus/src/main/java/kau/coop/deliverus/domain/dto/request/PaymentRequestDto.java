package kau.coop.deliverus.domain.dto.request;

import lombok.Getter;
import lombok.ToString;

@ToString
@Getter
public class PaymentRequestDto {
    private Long partyId;
    private String nickname;
}
