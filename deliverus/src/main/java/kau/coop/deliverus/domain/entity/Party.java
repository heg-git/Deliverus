package kau.coop.deliverus.domain.entity;

import jakarta.persistence.*;
import lombok.*;

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
    private Long expireTime;

    @OneToMany(mappedBy="party", cascade = CascadeType.ALL)
    private List<PartyMember> partyMembers;

    public void addPartyMember(PartyMember partyMember){
        this.partyMembers.add(partyMember);
        partyMember.setParty(this);
    }

}
