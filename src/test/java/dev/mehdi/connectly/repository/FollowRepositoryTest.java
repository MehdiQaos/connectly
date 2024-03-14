package dev.mehdi.connectly.repository;

import dev.mehdi.connectly.model.Member;
import dev.mehdi.connectly.service.MemberService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

//@DataJpaTest
//@SpringBootTest
class FollowRepositoryTest {
//    @Autowired
//    FollowRepository followRepository;
//    @Autowired
//    MemberService memberService;
//    Member follower;
//    Member following;
//
//    @BeforeEach
//    void setUp() {
//        follower = memberService.findById(1L).orElse(null);
//        following = memberService.findById(2L).orElse(null);
//    }
//
//    @AfterEach
//    void tearDown() {
//    }
//
//    @Test
//    void membersNotNull() {
//        assertThat(follower).isNotNull();
//        assertThat(following).isNotNull();
//        assertThat(follower.getFirstName()).isEqualTo("El Mehdi");
//        assertThat(following.getFirstName()).isEqualTo("Mohammed");
//    }
//
//    @Test
//    void findByFollowerIdAndFollowingId() {
//        Follow follow = Follow.builder()
//                .follower(follower)
//                .following(following)
//                .build();
//        followRepository.save(follow);
//        Follow found = followRepository
//                .findByFollowerAndFollowing(follower, following).orElse(null);
//        assertThat(found).isNotNull();
//    }
}