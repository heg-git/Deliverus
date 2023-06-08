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
import org.springframework.web.socket.messaging.SessionConnectedEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

import java.sql.Timestamp;
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
        sendMessage(1L, requestDto.getSender(), requestDto.getChat(), requestDto.getChannelId());
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
                chatRepository.findMemberByName(sender);
                if(chatRepository.isLeaved(sender, channelId)){
                    log.info("채팅을 친사람이고 나갔다 들어왔으므로 여기 실행");
                    Optional<Timestamp> timestamp = chatRepository.findLastEntranceTime(sender);
                    Optional<List<ChatMessage>> optionalChatMessages = chatRepository.loadChatMessages(sender, channelId, timestamp.get());
                    List<ChatMessage> chatMessageList = optionalChatMessages.get();
                    ChatMessage lastChatMessage = chatMessageList.get(0);

                    for(ChatMessage cm : chatMessageList) if(cm.getSender().equals(sender)) lastChatMessage = cm;

                    if(lastChatMessage.getType().equals(-1L)) {
                        sendMessage(0L, sender, sender+"님이 입장하셨습니다!", channelId);
                        log.info("나갔다 온 사람이며 한 번만 공지");
                    }
                }

            } catch (NoResultException | EmptyResultDataAccessException e){
                log.info("알림: "+e.getMessage());
                sendMessage(0L, sender, sender+"님이 입장하셨습니다!", channelId);
            }
        }
    }

    @Override
    public void disconnectedEvent(SessionDisconnectEvent event) {
        //연결 끊겼을 시 이벤트 함수
    }

    @Override
    public List<ChatMessageResponseDto> loadChatMessages(String name, Long id) throws Exception {

        List<ChatMessageResponseDto> responseDto = new ArrayList<>();

        Optional<Timestamp> timestamp = chatRepository.findLastEntranceTime(name);
        if(timestamp.isEmpty()) throw new Exception("Not a participants");
        log.info("마지막 입장시간: "+timestamp.get().toString());
        Optional<List<ChatMessage>> chatMessages = chatRepository.loadChatMessages(name, id, timestamp.get());
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

    @Override
    public void sendMessage(Long type, String sender, String chat, Long id){

        ChatMessage chatMessage = ChatMessage.builder()
                .time(ChatMessage.setTime())
                .chat(chat)
                .sender(sender)
                .type(type)
                .build();
        log.info("전송 되는 메세지 : " + chatMessage.toString());
        chatRepository.saveMessage(chatMessage, id);
        simpMessagingTemplate.convertAndSend("/sub/chat/" + id, chatMessage);
    }

}
