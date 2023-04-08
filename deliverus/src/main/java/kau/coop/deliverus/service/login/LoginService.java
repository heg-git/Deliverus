package kau.coop.deliverus.service.login;

import kau.coop.deliverus.domain.entity.Member;

public interface LoginService {

    Member login(String userid, String passwd) throws Exception;
}
