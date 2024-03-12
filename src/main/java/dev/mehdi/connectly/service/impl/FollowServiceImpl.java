package dev.mehdi.connectly.service.impl;

import dev.mehdi.connectly.exception.InvalidRequestException;
import dev.mehdi.connectly.exception.ResourceNotFoundException;
import dev.mehdi.connectly.model.Follow;
import dev.mehdi.connectly.model.Member;
import dev.mehdi.connectly.repository.FollowRepository;
import dev.mehdi.connectly.service.FollowService;
import dev.mehdi.connectly.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FollowServiceImpl implements FollowService {
    private final FollowRepository followRepository;
    private final MemberService memberService;

    @Override
    public void follow(Long followerId, Long followedId) {
        Member follower = memberService.findById(followerId).orElseThrow(
                () -> new ResourceNotFoundException("follower not found")
        );
        Member following = memberService.findById(followedId).orElseThrow(
                () -> new ResourceNotFoundException("following not found")
        );
        if (followRepository.findByFollowerAndFollowing(follower, following)
                .isPresent()) {
            throw InvalidRequestException.of("follow", "You already follow this user");
        }
        Follow follow = Follow.builder()
                .follower(follower)
                .following(following)
                .build();
        followRepository.save(follow);
    }

    @Override
    public List<Member> getFollowers(Long id) {
        Member member = memberService.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("member not found")
        );
        return followRepository.findAllByFollowing(member)
                .stream().map(Follow::getFollower).toList();
    }

    @Override
    public List<Member> getFollowing(Long id) {
        Member member = memberService.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("member not found")
        );
        return followRepository.findAllByFollower(member)
                .stream().map(Follow::getFollower).toList();
    }

    @Override
    public void unfollow(Long followerId, Long followedId) {
        Member follower = memberService.findById(followerId).orElseThrow(
                () -> new ResourceNotFoundException("follower not found")
        );
        Member following = memberService.findById(followedId).orElseThrow(
                () -> new ResourceNotFoundException("following not found")
        );
        Follow follow = followRepository.findByFollowerAndFollowing(follower, following)
                .orElseThrow(() -> new ResourceNotFoundException("You don't follow this user"));
        followRepository.delete(follow);
    }
}
