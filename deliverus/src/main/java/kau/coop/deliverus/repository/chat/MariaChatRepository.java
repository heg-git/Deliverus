package kau.coop.deliverus.repository.chat;

import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.TypedQuery;
import kau.coop.deliverus.domain.entity.ChatMessage;
import kau.coop.deliverus.domain.entity.Party;
import kau.coop.deliverus.domain.entity.PartyMember;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.List;
import java.util.Objects;
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

//    @Override
//    public Optional<String> findMemberByName(String name, Long id) {
//        return Optional.of(em.createQuery("select pm.nickname from PartyMember pm where pm.party.partyId=:id and pm.nickname=:name", String.class)
//                .setMaxResults(1)
//                .setParameter("name", name)
//                .setParameter("id", id)
//                .getSingleResult());
//    }

    @Override
    public Optional<List<ChatMessage>> loadChatMessages(String name, Long id, Timestamp timestamp) {
        //if(em.createQuery("select p.partyId from Party p )
        return Optional.of(em.createQuery("select cm from Party p Join p.chatMessages cm where cm.time>=:time and p.id=:id", ChatMessage.class)
                .setParameter("time", timestamp)
                .setParameter("id", id)
                .getResultList());
    }

    @Override
    public Optional<Timestamp> findLastEntranceTime(String name) {

        return Optional.of(em.createQuery("select distinct cm.time from Party p JOIN p.chatMessages cm where cm.sender=:name and cm.type=0L order by cm.time desc", Timestamp.class)
                .setMaxResults(1)
                .setParameter("name",name)
                .getSingleResult());

    }

    @Override
    public boolean isLeaved(String name, Long id) {
            Optional<List<ChatMessage>> messages = Optional.of(em.createQuery("select cm from Party p Join p.chatMessages cm where p.partyId=:id and cm.sender=:name and cm.type=-1L", ChatMessage.class)
                    .setParameter("id",id)
                    .setParameter("name",name)
                    .getResultList());
            return !messages.get().isEmpty();
    }

}
