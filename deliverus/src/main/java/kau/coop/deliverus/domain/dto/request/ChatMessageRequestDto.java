package kau.coop.deliverus.domain.dto.request;

import lombok.*;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ChatMessageRequestDto {

    private String sender;
    private Long channelId;
    private String chat;

}
