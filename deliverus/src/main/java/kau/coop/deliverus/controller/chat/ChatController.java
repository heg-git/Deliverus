package kau.coop.deliverus.controller.chat;

import kau.coop.deliverus.domain.entity.Message;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
public class ChatController {

    private final SimpMessagingTemplate simpMessagingTemplate;
    /*
     * /sub/channel/sam -구독(channelId: sam)
     * /pub/hello -메시지 발행
     * */

    @MessageMapping({"/chat"})
    public void sendMessage(Message message, SimpMessageHeaderAccessor accessor){
        log.info("여기 실행! " + message.toString());
        simpMessagingTemplate.convertAndSend("/sub/chat/"+message.getChannelId(), message);
    }

}
