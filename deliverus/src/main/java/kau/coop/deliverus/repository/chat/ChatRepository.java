package kau.coop.deliverus.repository.chat;

import kau.coop.deliverus.domain.entity.ChatMessage;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

public interface ChatRepository {

    void saveMessage(ChatMessage chatMessage, Long chatId);

    Optional<String> findMemberByName(String name);

    Optional<List<ChatMessage>> loadChatMessages(String name, Timestamp timestamp);

    Optional<Timestamp> findEntranceTime(String name);
}
