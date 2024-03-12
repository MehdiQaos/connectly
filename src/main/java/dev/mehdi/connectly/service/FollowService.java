package dev.mehdi.connectly.service;

import dev.mehdi.connectly.model.Member;

import java.util.List;

public interface FollowService {
    void follow(Long followerId, Long followedId);

    List<Member> getFollowers(Long id);

    List<Member> getFollowing(Long id);

    void unfollow(Long followerId, Long followedId);
}
