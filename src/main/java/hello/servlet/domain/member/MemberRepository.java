package hello.servlet.domain.member;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 동시성 문제가 고려되어 있지 않음, 실무에서는 ConcurrentHashMap, AtomicLong 사용 고려
 * MemberRepository 는 싱글톤 패턴을 적용했다.
 * 스프링을 사용할 때는 스프링 빈으로 등록하면 싱글톤 패턴이 된다.
 */
public class MemberRepository {
    // repository 가 아무리 new 로 많이 생성되어도 static 으로 선언된 아래 애들은 1개만 생성된다.
    private static Map<Long, Member> store = new HashMap<>();
    private static long sequence = 0L;

    private static final MemberRepository instance = new MemberRepository();

    public static MemberRepository getInstance() {
        return instance;
    }

    private MemberRepository() { // 싱글톤 패턴으로 만들 때는 객체를 아무나 생성하지 못하게 private 접근자로 생성자를 막아야 한다.
    }

    public Member save(Member member) {
        member.setId(++sequence); // Member 를 회원 저장소에 저장하면 회원 저장소가 할당한다.
        store.put(member.getId(), member);
        return member;
    }

    public Member findById(Long id) {
        return store.get(id);
    }

    public List<Member> findAll() {
        // new ArrayList 에 값을 넣거나 조작해도 store 에 있는 value list 를 건들고 싶지 않음
        return new ArrayList<>(store.values());
    }

    public void clearStore() {
        store.clear();
    }
}