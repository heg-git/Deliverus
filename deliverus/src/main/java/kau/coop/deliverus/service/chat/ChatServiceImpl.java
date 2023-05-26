package kau.coop.deliverus.service.chat;

import jakarta.persistence.NoResultException;
import kau.coop.deliverus.domain.dto.request.ChatMessageRequestDto;
import kau.coop.deliverus.domain.dto.response.ChatMessageResponseDto;
import kau.coop.deliverus.domain.entity.ChatMessage;
import kau.coop.deliverus.repository.chat.ChatRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.support.GenericMessage;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.ObjectError;
import org.springframework.web.socket.messaging.SessionConnectedEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

import java.sql.Timestamp;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class ChatServiceImpl implements ChatService {

    private final SimpMessagingTemplate simpMessagingTemplate;
    private final ChatRepository chatRepository;

    @Override
    public void sendMessage(ChatMessageRequestDto requestDto) {

        ChatMessage chatMessage = ChatMessage.builder()
                .type(1L)
                .time(setTime())
                .sender(requestDto.getSender())
                .chat(requestDto.getChat())
                .build();

        chatRepository.saveMessage(chatMessage, requestDto.getChannelId());
        simpMessagingTemplate.convertAndSend("/sub/chat/" + requestDto.getChannelId(), chatMessage);
    }

    @Override
    public void connectedEvent(SessionConnectedEvent event) throws Exception{

        MessageHeaders messageHeaders = event.getMessage().getHeaders();
        GenericMessage<?> genericMessage = messageHeaders.get("simpConnectMessage", GenericMessage.class);

        if (genericMessage != null) {
            Map nativeHeaders = genericMessage.getHeaders().get("nativeHeaders", Map.class);

            String id = nativeHeaders.get("channelId").toString();
            id = id.substring(1, id.length()-1);
            Long channelId = Long.parseLong(id);

            String sender = nativeHeaders.get("sender").toString();
            sender = sender.substring(1, sender.length()-1);

            try {
                if (chatRepository.findMemberByName(sender).isPresent()) log.info("연결은 되었지만 이미 데이터가 존재하므로 공지x");
                else throw new Exception("Header error!");
            } catch (NoResultException | EmptyResultDataAccessException e){
                ChatMessage chatMessage = ChatMessage.builder()
                        .type(0L)
                        .time(setTime())
                        .sender(sender)
                        .chat(sender+"님이 입장하셨습니다!")
                        .build();
                log.info("입장: "+chatMessage.toString());
                chatRepository.saveMessage(chatMessage, channelId);
                simpMessagingTemplate.convertAndSend("/sub/chat/" + channelId, chatMessage);
            }
        }

    }

    @Override
    public void disconnectedEvent(SessionDisconnectEvent event) {

    }

    @Override
    public List<ChatMessageResponseDto> loadChatMessages(String name) throws Exception {

        List<ChatMessageResponseDto> responseDto = new ArrayList<>();

        Optional<Timestamp> timestamp = chatRepository.findEntranceTime(name);

        if(timestamp.isEmpty()) throw new Exception("Not a participants");
        Optional<List<ChatMessage>> chatMessages = chatRepository.loadChatMessages(name, timestamp.get());
        if(chatMessages.isEmpty()) throw new Exception("No messages");

        for(ChatMessage c : chatMessages.get()){
            ChatMessageResponseDto cm = ChatMessageResponseDto.builder()
                    .type(c.getType())
                    .time(c.getTime())
                    .sender(c.getSender())
                    .chat(c.getChat())
                    .build();
            responseDto.add(cm);
        }
        return responseDto;
    }

    private Timestamp setTime(){
        ZoneId zoneId = ZoneId.of("Asia/Seoul");
        ZonedDateTime zonedDateTime = ZonedDateTime.now(zoneId);
        return Timestamp.valueOf(zonedDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
    }
}
