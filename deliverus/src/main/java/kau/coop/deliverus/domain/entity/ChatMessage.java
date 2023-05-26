package kau.coop.deliverus.domain.entity;

import jakarta.persistence.Embeddable;
import lombok.*;

import java.sql.Timestamp;

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

}
