package kau.coop.deliverus.repository.member;

import kau.coop.deliverus.domain.entity.Member;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

public class MemoryMemberRepository implements MemberRepository {

    private Map<Long, Member> store = new ConcurrentHashMap();

    private static long sequence = 0L;
    @Override
    public Member join(Member member) {
        member.setId(sequence++);
        store.put(member.getId(), member);
        return member;
    }

    @Override
    public Optional<Member> findById(Long id) {
        return Optional.ofNullable(store.get(id));
    }

    @Override
    public Optional<Member> findByNickname(String nickname) {
        return store.values().stream()
                .filter(member -> member.getId().equals(nickname))
                .findAny();
    }

    @Override
    public Optional<Member> findByUserid(String userid) {
        return store.values().stream()
                .filter(member -> member.getUserid().equals(userid))
                .findAny();
    }

    @Override
    public List<Member> findAll() {
        return new ArrayList<>(store.values());
    }
}
