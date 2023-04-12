package kau.coop.deliverus.domain.model;

import lombok.Getter;

@Getter
public class MemberModel {

    private String nickname;

    public MemberModel(String nickname) {
        this.nickname = nickname;
    }
}
