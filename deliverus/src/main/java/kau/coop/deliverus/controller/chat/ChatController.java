package kau.coop.deliverus.controller.chat;

import kau.coop.deliverus.domain.dto.request.ChatMessageRequestDto;
import kau.coop.deliverus.domain.dto.response.ChatMessageResponseDto;
import kau.coop.deliverus.service.chat.ChatService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.socket.messaging.SessionConnectedEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
public class ChatController {

    private final ChatService chatService;

    @MessageMapping({"/chat"})
    public void sendMessage(ChatMessageRequestDto requestDto){
        chatService.sendMessage(requestDto);
        log.info("메세지 전송"+ requestDto.toString());
    }

    @GetMapping("api/chat/message")
    public ResponseEntity<List<ChatMessageResponseDto>> getMessageList(@RequestParam("name") String name){
        try {
            List<ChatMessageResponseDto> results = chatService.loadChatMessages(name);
            return new ResponseEntity<>(results, HttpStatus.OK);
        } catch (Exception e) {
            log.info(e.getMessage());
            return new ResponseEntity<>(new ArrayList<>(), HttpStatus.OK);
        }
    }

    @EventListener
    public void connected(SessionConnectedEvent event){
        try {
            chatService.connectedEvent(event);
            log.info("연결되었다");
        }catch (Exception ignored){
            log.info("오류 발생 "+ignored.getMessage());
        }
    }

    @EventListener
    public void disconnected(SessionDisconnectEvent event){
//        StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());
//        String username = (String) headerAccessor.getSessionAttributes().get("username");

        log.info("연결이 끊겼다."+event.toString());
    }

}
