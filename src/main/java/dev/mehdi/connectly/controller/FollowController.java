package dev.mehdi.connectly.controller;

import dev.mehdi.connectly.dto.member.MemberResponseDto;
import dev.mehdi.connectly.mapper.MemberMapper;
import dev.mehdi.connectly.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/follows")
@RequiredArgsConstructor
public class FollowController {
    private final MemberService memberService;
    private final MemberMapper memberMapper;

    @GetMapping("/follow/{followerId}/{followedId}")
    public void follow(@PathVariable Long followerId, @PathVariable Long followedId) {
        memberService.follow(followerId, followedId);
    }

    @GetMapping("/unfollow/{followerId}/{followedId}")
    public void unfollow(@PathVariable Long followerId, @PathVariable Long followedId) {
        memberService.unfollow(followerId, followedId);
    }

    @GetMapping("{id}/followers")
    public ResponseEntity<List<MemberResponseDto>> getFollowers(@PathVariable Long id) {
        List<MemberResponseDto> dtoList = memberService.getFollowers(id)
                .stream().map(memberMapper::toDto).toList();
        return ResponseEntity.ok(dtoList);
    }

    @GetMapping("{id}/followings")
    public ResponseEntity<List<MemberResponseDto>> getFollowing(@PathVariable Long id) {
        List<MemberResponseDto> dtoList = memberService.getFollowing(id)
                .stream().map(memberMapper::toDto).toList();
        return ResponseEntity.ok(dtoList);
    }
}
