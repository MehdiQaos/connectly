package dev.mehdi.connectly.service.impl;

import dev.mehdi.connectly.exception.ResourceNotFoundException;
import dev.mehdi.connectly.model.Member;
import dev.mehdi.connectly.repository.MemberRepository;
import dev.mehdi.connectly.service.FollowService;
import dev.mehdi.connectly.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FollowServiceImpl implements FollowService {
    private final MemberService memberService;
    private final MemberRepository memberRepository;

    @Override
    public void follow(Long followerId, Long followedId) {
        Member follower = memberService.findById(followerId).orElseThrow(
                () -> new ResourceNotFoundException("follower not found")
        );
        Member following = memberService.findById(followedId).orElseThrow(
                () -> new ResourceNotFoundException("following not found")
        );
        follower.follow(following);
        memberRepository.save(follower);
    }

    @Override
    public List<Member> getFollowers(Long id) {
        Member member = memberService.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("member not found")
        );
        return member.getFollowers().stream().toList();
    }

    @Override
    public List<Member> getFollowing(Long id) {
        Member member = memberService.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("member not found")
        );
        return member.getFollowings().stream().toList();
    }

    @Override
    public void unfollow(Long followerId, Long followedId) {
        Member follower = memberService.findById(followerId).orElseThrow(
                () -> new ResourceNotFoundException("follower not found")
        );
        Member following = memberService.findById(followedId).orElseThrow(
                () -> new ResourceNotFoundException("following not found")
        );
        follower.unfollow(following);
        memberRepository.save(follower);
    }
}
