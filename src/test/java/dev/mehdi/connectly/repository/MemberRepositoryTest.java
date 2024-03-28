package dev.mehdi.connectly.repository;

import dev.mehdi.connectly.model.Member;
import dev.mehdi.connectly.model.Role;
import dev.mehdi.connectly.model.enums.MemberRole;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class MemberRepositoryTest {
    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private RoleRepository roleRepository;

    private Member member1, member2, member3, member4, member5;
    private Role admin, user;

    @BeforeEach
    void setUp() {
        admin = new Role(null, MemberRole.ADMIN);
        user = new Role(null, MemberRole.USER);

        member1 = new Member(null, "El Mehdi", "Qaos", "m@q.com", "123456", LocalDate.of(1999, 1, 1), true, null, null, null, null, user, null, null, null, null);
        member2 = new Member(null, "John", "Doe", "j@d.com", "123456", LocalDate.of(1990, 1, 1), true, null, null, null, null, user, null, null, null, null);
        member3 = new Member(null, "Jane", "Doe", "jane@d.com", "123456", LocalDate.of(1992, 1, 1), true, null, null, null, null, admin, null, null, null, null);
        member4 = new Member(null, "Alice", "Smith", "a@s.net", "123456", LocalDate.of(1995, 1, 1), true, null, null, null, null, user, null, null, null, null);
        member5 = new Member(null, "Bob", "Johnson", "b@j.com", "123456", LocalDate.of(1988, 1, 1), true, null, null, null, null, admin, null, null, null, null);

        roleRepository.saveAll(List.of(admin, user));
        memberRepository.saveAll(List.of(member1, member2, member3, member4, member5));
    }

    @AfterEach
    void tearDown() {
        memberRepository.deleteAll();
        roleRepository.deleteAll();
    }

    @Test
    void otherAccountHasName_shouldReturnTrue() {
        boolean result = memberRepository.otherAccountHasName(member1.getFirstName(), member1.getLastName(), member1.getId());
        assertThat(result).isFalse();
    }

    @Test
    void otherAccountHasName_shouldReturnFalse() {
        boolean result = memberRepository.otherAccountHasName(member2.getFirstName(), member2.getLastName(), member1.getId());
        assertThat(result).isTrue();
    }

    @Test
    void searchByNameOrEmail_shouldReturnMembers() {
        List<Member> result = memberRepository.searchByNameOrEmail("com");
        SoftAssertions assertions = new SoftAssertions();
        assertions.assertThat(result).hasSize(4);
        assertions.assertThat(result).contains(member1, member2, member3, member5);
        assertions.assertThat(result).doesNotContain(member4);
        assertions.assertAll();
    }

    @Test
    void searchByNameOrEmail_shouldReturnEmpty() {
        List<Member> result = memberRepository.searchByNameOrEmail("zdflkjasdlkajsdfklj");
        assertThat(result).isEmpty();
    }
}