package kau.coop.deliverus.domain.entity;

import jakarta.persistence.*;
import lombok.Getter;

@Getter
@Entity
public class Member {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column
    String nickname;

    @Column
    String userid;

    @Column
    String passwd;

    public Member(){}

    public Member(String nickname, String userid, String passwd) {
        this.nickname = nickname;
        this.userid = userid;
        this.passwd = passwd;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
