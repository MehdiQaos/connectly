package dev.mehdi.connectly.repository;

import dev.mehdi.connectly.model.Follow;
import dev.mehdi.connectly.model.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface FollowRepository extends JpaRepository<Follow, Long> {
    Optional<Follow> findByFollowerAndFollowing(Member follower, Member following);

    List<Follow> findAllByFollowing(Member member);

    List<Follow> findAllByFollower(Member member);
}