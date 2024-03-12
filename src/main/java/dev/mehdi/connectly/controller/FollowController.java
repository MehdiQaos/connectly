package dev.mehdi.connectly.controller;

import dev.mehdi.connectly.dto.member.MemberResponseDto;
import dev.mehdi.connectly.mapper.MemberMapper;
import dev.mehdi.connectly.service.FollowService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/follows")
@RequiredArgsConstructor
public class FollowController {
    private final FollowService followService;
    private final MemberMapper memberMapper;

    @GetMapping("/follow/{followerId}/{followedId}")
    public void follow(@PathVariable Long followerId, @PathVariable Long followedId) {
        followService.follow(followerId, followedId);
    }

    @GetMapping("/unfollow/{followerId}/{followedId}")
    public void unfollow(@PathVariable Long followerId, @PathVariable Long followedId) {
        followService.unfollow(followerId, followedId);
    }

    @GetMapping("{id}/followers")
    public ResponseEntity<List<MemberResponseDto>> getFollowers(@PathVariable Long id) {
        List<MemberResponseDto> dtoList = followService.getFollowers(id)
                .stream().map(memberMapper::toMemberResponseDto).toList();
        return ResponseEntity.ok(dtoList);
    }

    @GetMapping("{id}/followings")
    public ResponseEntity<List<MemberResponseDto>> getFollowing(@PathVariable Long id) {
        List<MemberResponseDto> dtoList = followService.getFollowing(id)
                .stream().map(memberMapper::toMemberResponseDto).toList();
        return ResponseEntity.ok(dtoList);
    }
}
