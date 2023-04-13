package kau.coop.deliverus.domain.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class RegisterRequestDto {

    private String nickname;
    private String userid;
    private String passwd;

    public RegisterRequestDto(String nickname, String userid, String passwd) {
        this.nickname = nickname;
        this.userid = userid;
        this.passwd = passwd;
    }
}
