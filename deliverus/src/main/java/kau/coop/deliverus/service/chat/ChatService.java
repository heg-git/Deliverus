package kau.coop.deliverus.service.chat;

import kau.coop.deliverus.domain.dto.request.ChatMessageRequestDto;
import kau.coop.deliverus.domain.dto.response.ChatMessageResponseDto;
import org.springframework.web.socket.messaging.SessionConnectedEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

import java.util.List;

public interface ChatService {

    void sendMessage(ChatMessageRequestDto requestDto);

    void sendMessage(Long type, String sender, String chat, Long id);

    void connectedEvent(SessionConnectedEvent event) throws Exception;

    void disconnectedEvent(SessionDisconnectEvent event);

    List<ChatMessageResponseDto> loadChatMessages(String name, Long id) throws Exception;

}
