package kau.coop.deliverus.repository.chat;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import kau.coop.deliverus.domain.entity.ChatMessage;
import kau.coop.deliverus.domain.entity.Party;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
@Slf4j
public class MariaChatRepository implements ChatRepository{

    private final EntityManager em;

    @Override
    public void saveMessage(ChatMessage chatMessage, Long chatId) {
        Party party = em.find(Party.class, chatId);
        party.addChatMessage(chatMessage);
        em.persist(party);
    }

    @Override
    public Optional<String> findMemberByName(String name) {
        return Optional.of(em.createQuery("select cm.sender from Party p Join p.chatMessages cm where cm.sender=:name", String.class)
                .setMaxResults(1)
                .setParameter("name", name)
                .getSingleResult());
    }

    @Override
    public Optional<List<ChatMessage>> loadChatMessages(String name, Timestamp timestamp) {
        return Optional.of(em.createQuery("select cm from Party p Join p.chatMessages cm where cm.time>=:time", ChatMessage.class)
                .setParameter("time", timestamp)
                .getResultList());
    }

    @Override
    public Optional<Timestamp> findEntranceTime(String name) {

        return Optional.of(em.createQuery("select distinct cm.time from Party p JOIN p.chatMessages cm where cm.sender=:name", Timestamp.class)
                .setMaxResults(1)
                .setParameter("name",name)
                .getSingleResult());

    }

}
