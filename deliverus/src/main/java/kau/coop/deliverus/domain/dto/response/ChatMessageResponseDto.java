package kau.coop.deliverus.domain.dto.response;

import lombok.*;

import java.sql.Timestamp;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ChatMessageResponseDto {

    private Timestamp time;
    private Long type;
    private String sender;
    private String chat;

}
