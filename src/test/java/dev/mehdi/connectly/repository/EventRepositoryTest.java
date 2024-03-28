package dev.mehdi.connectly.repository;

import dev.mehdi.connectly.model.*;
import dev.mehdi.connectly.model.enums.EventType;
import dev.mehdi.connectly.model.enums.MemberRole;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class EventRepositoryTest {
    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private PostRepository postRepository;

    private Member member1;
    private Member member2;
    private Member member3;

    private Role admin;
    private Role user;

    private Post post1;
    private Post post2;
    private Post post3;

    private Comment comment1;
    private Comment comment2;
    private Comment comment3;

    private Event event1;
    private Event event2;
    private Event event3;
    private Event event4;
    private Event event5;
    private Event event6;
    private Event event7;

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

        comment1 = new Comment(null, "Comment 1 content", member1, post1);
        comment2 = new Comment(null, "Comment 2 content", member2, post1);
        comment3 = new Comment(null, "Comment 3 content", member3, post2);

        event1 = new Event(null, EventType.FOLLOW, null, null, member1, member2);
        event2 = new Event(null, EventType.FOLLOW, null, null, member1, member3);
        event3 = new Event(null, EventType.FOLLOW, null, null, member2, member1);
        event4 = new Event(null, EventType.LIKE, post1, null, member1, post1.getMember());
        event5 = new Event(null, EventType.COMMENT, null, comment2, member2, comment2.getPost().getMember());
        event6 = new Event(null, EventType.COMMENT, null, comment1, member1, comment1.getPost().getMember());
        event7 = new Event(null, EventType.LIKE, post3, null, member1, post3.getMember());

        roleRepository.saveAll(List.of(admin, user));
        memberRepository.saveAll(List.of(member1, member2, member3));
        postRepository.saveAll(List.of(post1, post2, post3));
        commentRepository.saveAll(List.of(comment1, comment2, comment3));
        eventRepository.saveAll(List.of(event1, event2, event3, event4, event5, event6, event7));
    }

    @AfterEach
    void tearDown() {
        eventRepository.deleteAll();
        commentRepository.deleteAll();
        postRepository.deleteAll();
        memberRepository.deleteAll();
        roleRepository.deleteAll();
    }

    @Test
    void testFindEventByComment() {
        Optional<Event> eventOptional = eventRepository.findByComment(comment1);
        assertThat(eventOptional).isPresent();
        assertThat(eventOptional.get()).isEqualTo(event6);
    }

    @Test
    void testFindEventByCommentWhenEventDoesNotExist() {
        Optional<Event> eventOptional = eventRepository.findByComment(comment3);
        assertThat(eventOptional).isEmpty();
    }

    @Test
    void testFindAllByCommentIdWhenEventExist() {
        List<Event> events1 = eventRepository.findAllByCommentId(comment1.getId());
        List<Event> events2 = eventRepository.findAllByCommentId(comment2.getId());
        SoftAssertions allAssertions = new SoftAssertions();
        allAssertions.assertThat(events1).isNotEmpty();
        allAssertions.assertThat(events1).containsExactly(event6);
        allAssertions.assertThat(events2).isNotEmpty();
        allAssertions.assertThat(events2).containsExactly(event5);
        allAssertions.assertAll();
    }

    @Test
    void testFindAllByCommentIdWhenEventDoesNotExist() {
        List<Event> events = eventRepository.findAllByCommentId(comment3.getId());
        assertThat(events).isEmpty();
    }

    @Test
    void findByInitiatingMemberAndPostWhenEventExist() {
        Optional<Event> eventOptional1 = eventRepository.findByInitiatingMemberAndPost(member1, post1);
        Optional<Event> eventOptional2 = eventRepository.findByInitiatingMemberAndPost(member1, post3);
        SoftAssertions allAssertions = new SoftAssertions();
        allAssertions.assertThat(eventOptional1).isPresent();
        allAssertions.assertThat(eventOptional2).isPresent();
        allAssertions.assertThat(eventOptional1.get()).isEqualTo(event4);
        allAssertions.assertThat(eventOptional2.get()).isEqualTo(event7);
        allAssertions.assertAll();
    }
}