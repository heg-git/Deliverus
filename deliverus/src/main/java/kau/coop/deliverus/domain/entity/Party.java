package kau.coop.deliverus.domain.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Cascade;

import java.util.List;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class Party {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long partyId;
    private String partyName;
    private String pickUpAddress;
    private String host;
    private Long restaurantId;
    private Long memberNum;
    private Double latitude;
    private Double longitude;
    private String expireTime;
    private Long state;
    private Long deliverTime;
    private Long life;

    @OneToMany(mappedBy="party", cascade = CascadeType.ALL)
    private List<PartyMember> partyMembers;

    @ElementCollection
    @Cascade(org.hibernate.annotations.CascadeType.REMOVE)
    @CollectionTable(
            name = "chatMessage",
            joinColumns = @JoinColumn(name = "chatId")
    )
    private List<ChatMessage> chatMessages;

    public void addChatMessage(ChatMessage chatMessage){
        this.chatMessages.add(chatMessage);
    }

    public void addPartyMember(PartyMember partyMember){
        this.partyMembers.add(partyMember);
        partyMember.setParty(this);
    }

    public void setState(Long state) {
        this.state = state;
    }
}
