package dev.mehdi.connectly.repository;

import dev.mehdi.connectly.model.Member;
import dev.mehdi.connectly.model.Post;
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
class PostRepositoryTest {
    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private RoleRepository roleRepository;

    private Member member1;
    private Member member2;
    private Member member3;

    private Role admin;
    private Role user;

    private Post post1;
    private Post post2;
    private Post post3;
    private Post post4;
    private Post post5;
    private Post post6;

    @BeforeEach
    void setUp() {
        admin = new Role(null, MemberRole.ADMIN);
        user = new Role(null, MemberRole.USER);

        member1 = new Member(null, "El Mehdi", "Qaos", "m@q.com", "123456", LocalDate.of(1999, 1, 1), true, null, null, null, null, user, null, null, null, null);
        member2 = new Member(null, "John", "Doe", "j@d.com", "123456", LocalDate.of(1990, 1, 1), true, null, null, null, null, user, null, null, null, null);
        member3 = new Member(null, "Jane", "Doe", "jane@d.com", "123456", LocalDate.of(1992, 1, 1), true, null, null, null, null, admin, null, null, null, null);

        post1 = new Post(null, null, "Post 1 content", member1, null);
        post2 = new Post(null, null, "Post 2 content", member2, null);
        post3 = new Post(null, null, "Post 3 content", member1, null);
        post4 = new Post(null, null, "Post 4 content", member2, null);
        post5 = new Post(null, null, "Post 5 content", member3, null);
        post6 = new Post(null, null, "Post 6 content", member2, null);

        roleRepository.saveAll(List.of(admin, user));
        memberRepository.saveAll(List.of(member1, member2, member3));
        postRepository.saveAll(List.of(post1, post2, post3, post4, post5, post6));
    }

    @AfterEach
    void tearDown() {
        postRepository.deleteAll();
        memberRepository.deleteAll();
        roleRepository.deleteAll();
    }

    @Test
    void testFindAllByMemberOrderByCreatedAtDesc_shouldContainPostsInOrder() {
        List<Post> posts = postRepository.findAllByMemberOrderByCreatedAtDesc(member1);
        SoftAssertions assertions = new SoftAssertions();
        assertions.assertThat(posts).isNotEmpty();
        assertions.assertThat(posts).hasSize(2);
        assertions.assertThat(posts).contains(post1);
        assertions.assertThat(posts).contains(post3);
        assertions.assertThat(posts).containsExactly(post3, post1);
        assertions.assertThat(posts).doesNotContain(post2, post4, post5, post6);
        assertions.assertAll();
    }

    @Test
    void findAllContaining_shouldFindPosts() {
        List<Post> posts = postRepository.findAllByContentContaining("Post");
        SoftAssertions assertions = new SoftAssertions();
        assertions.assertThat(posts).isNotEmpty();
        assertions.assertThat(posts).hasSize(6);
        assertions.assertThat(posts).contains(post1, post2, post3, post4, post5, post6);
        assertions.assertAll();
    }

    @Test
    void findAllContaining_shouldNotFindPosts() {
        List<Post> posts = postRepository.findAllByContentContaining("not found");
        assertThat(posts).isEmpty();
    }

    @Test
    void shouldFindPostsByMembers() {
        List<Post> posts = postRepository.findByMemberInOrderByCreatedAtDesc(List.of(member2, member3));
        SoftAssertions assertions = new SoftAssertions();
        assertions.assertThat(posts).isNotEmpty();
        assertions.assertThat(posts).hasSize(4);
        assertions.assertThat(posts).containsExactly(post6, post5, post4, post2);
        assertions.assertThat(posts).doesNotContain(post1, post3);
        assertions.assertAll();
    }

    @Test
    void findByMemberNotInOrderByCreatedAtDesc() {
        List<Post> posts = postRepository.findByMemberNotInOrderByCreatedAtDesc(List.of(member2, member3));
        SoftAssertions assertions = new SoftAssertions();
        assertions.assertThat(posts).isNotEmpty();
        assertions.assertThat(posts).hasSize(2);
        assertions.assertThat(posts).containsExactly(post3, post1);
        assertions.assertThat(posts).doesNotContain(post2, post4, post5, post6);
        assertions.assertAll();
    }
}