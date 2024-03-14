package dev.mehdi.connectly.controller;

import dev.mehdi.connectly.dto.member.SimpleMember;
import dev.mehdi.connectly.exception.ResourceNotFoundException;
import dev.mehdi.connectly.mapper.MemberMapper;
import dev.mehdi.connectly.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/members")
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;
    private final MemberMapper memberMapper;

    @GetMapping
    public ResponseEntity<List<SimpleMember>> getMembers() {
        List<SimpleMember> dtoList = memberService.getMembers()
                .stream().map(memberMapper::toSimpleMember).toList();
        return ResponseEntity.ok(dtoList);
    }

    @GetMapping("/{id}")
    public ResponseEntity<SimpleMember> getMember(@PathVariable Long id) {
        SimpleMember member = memberService.findById(id).map(memberMapper::toSimpleMember)
                .orElseThrow(
                        () -> new ResourceNotFoundException("Member not found")
                );
        return ResponseEntity.ok(member);
    }
}