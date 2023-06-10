package kau.coop.deliverus.domain.entity;

import jakarta.persistence.Embeddable;
import lombok.*;

import java.sql.Timestamp;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Embeddable
public class ChatMessage {

    private Timestamp time;
    private Long type;
    private String sender;
    private String chat;

    public static Timestamp setTime(){
        ZoneId zoneId = ZoneId.of("Asia/Seoul");
        ZonedDateTime zonedDateTime = ZonedDateTime.now(zoneId);
        return Timestamp.valueOf(zonedDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
    }

}
