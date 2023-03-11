package hello.servlet.domain.member;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

class MemberRepositoryTest { // JUnit5 부터는 test class 이름에 public 이 없어도 된다. JUnit4 까지는 있어야 했다.

    MemberRepository memberRepository = MemberRepository.getInstance();

    @AfterEach
    void afterEach() { // 테스트가 끝날 때 마다 AfterEach 메서드가 실행된다.
        memberRepository.clearStore(); // test class 내부 메서드들의 순서가 보장되지 않으므로 clearStore 작업이 필요하다.
    }

    @Test
    void save() {
        // given
        Member member = new Member("hello", 20);

        // when
        Member savedMember = memberRepository.save(member);

        // then
        Member findMember = memberRepository.findById(savedMember.getId());
        assertThat(findMember).isEqualTo(savedMember);
    }

    @Test
    void findAll() {
        // given
        Member member1 = new Member("member1", 20);
        Member member2 = new Member("member2", 30);

        memberRepository.save(member1);
        memberRepository.save(member2);

        // when
        List<Member> result = memberRepository.findAll();

        // then
        assertThat(result.size()).isEqualTo(2);
        assertThat(result).contains(member1, member2);
    }
}