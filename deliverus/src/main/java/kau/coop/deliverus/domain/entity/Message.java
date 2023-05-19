package kau.coop.deliverus.domain.entity;

import lombok.*;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Message {
    private Integer sender;
    private Integer channelId;
    private String chat;

    public void setSender(Integer sender){
        this.sender=sender;
    }


}
