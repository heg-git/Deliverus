package kau.coop.deliverus.domain.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class RegisterResponseDto {

    private String id;
    private String nickname;
}
