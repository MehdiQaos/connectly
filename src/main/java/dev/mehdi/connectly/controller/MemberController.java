package dev.mehdi.connectly.controller;

import dev.mehdi.connectly.dto.member.EditProfileDto;
import dev.mehdi.connectly.dto.member.MemberResponseDto;
import dev.mehdi.connectly.dto.member.ProfileDto;
import dev.mehdi.connectly.exception.ResourceNotFoundException;
import dev.mehdi.connectly.mapper.MemberMapper;
import dev.mehdi.connectly.model.Member;
import dev.mehdi.connectly.service.FileStorageService;
import dev.mehdi.connectly.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/members")
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;
    private final MemberMapper memberMapper;
    private final FileStorageService fileStorageService;

    @GetMapping
    public ResponseEntity<List<MemberResponseDto>> getMembers() {
        List<MemberResponseDto> dtoList = memberService.getMembers()
                .stream().map(memberMapper::toDto).toList();
        return ResponseEntity.ok(dtoList);
    }

    @GetMapping("/{id}")
    public ResponseEntity<MemberResponseDto> getMember(@PathVariable Long id) {
        MemberResponseDto member = memberService.findById(id).map(memberMapper::toDto)
                .orElseThrow(
                        () -> new ResourceNotFoundException("Member not found")
                );
        return ResponseEntity.ok(member);
    }

    @GetMapping("/{followerId}/follow/{followedId}")
    public void follow(@PathVariable Long followerId, @PathVariable Long followedId) {
        memberService.follow(followerId, followedId);
    }

    @GetMapping("/{followerId}/unfollow/{followedId}")
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

    @GetMapping("/profile/{id}")
    public ResponseEntity<ProfileDto> getProfile(@PathVariable Long id) {
        return memberService.findById(id).map(memberMapper::toProfileDto)
                .map(ResponseEntity::ok)
                .orElseThrow(
                        () -> new ResourceNotFoundException("Profile not found")
                );
    }

    @GetMapping("email/{email}")
    ResponseEntity<MemberResponseDto> byEmail(@PathVariable String email) {
        Member member = memberService.getByEmail(email).orElseThrow(
                () -> new ResourceNotFoundException("Member not found")
        );
        MemberResponseDto memberResponseDto = memberMapper.toDto(member);
        return ResponseEntity.ok().body(memberResponseDto);
    }

    @PostMapping("/{member1Id}/isFollowing/{member2Id}")
    public ResponseEntity<Boolean> isFollowing(@PathVariable Long member1Id, @PathVariable Long member2Id) {
        return ResponseEntity.ok(memberService.isFollowing(member1Id, member2Id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<MemberResponseDto> updateMember(@PathVariable Long id, @RequestBody EditProfileDto dto) {
        Member updatedMember = memberService.update(id, dto);
        MemberResponseDto responseDto = memberMapper.toDto(updatedMember);
        return ResponseEntity.ok(responseDto);
    }

    @PatchMapping("/{id}/email")
    public ResponseEntity<MemberResponseDto> updateEmail(@PathVariable Long id, @RequestParam String email) {
        Member updatedMember = memberService.updateEmail(id, email);
        MemberResponseDto responseDto = memberMapper.toDto(updatedMember);
        return ResponseEntity.ok(responseDto);
    }

    @PatchMapping("/{id}/password")
    public ResponseEntity<MemberResponseDto> updatePassword(@RequestParam String oldPassword, @RequestParam String newPassword, @PathVariable Long id) {
        Member updatedMember = memberService.updatePassword(oldPassword, newPassword, id);
        MemberResponseDto responseDto = memberMapper.toDto(updatedMember);
        return ResponseEntity.ok(responseDto);
    }

    @PostMapping("/{id}/picture")
    public ResponseEntity<MemberResponseDto> uploadProfilePicture(@PathVariable Long id, @RequestParam("file") MultipartFile file) {
        Member member = memberService.updateProfilePicture(id, file);
        return ResponseEntity.ok(memberMapper.toDto(member));
    }

    @GetMapping("/search")
    public ResponseEntity<List<MemberResponseDto>> search(@RequestParam(required = false, defaultValue = "") String query) {
        List<MemberResponseDto> dtoList = memberService.search(query)
                .stream().map(memberMapper::toDto).toList();
        return ResponseEntity.ok(dtoList);
    }

//    @PostMapping("/{id}/profile-picture")
//    public ResponseEntity<String> uploadProfilePicture(@PathVariable Long id, @RequestParam("file") MultipartFile file) {
//        String url = memberService.storeProfilePicture(id, file);
//        return ResponseEntity.ok(url);
//    }
//
//    @PostMapping("/{id}/cover-picture")
//    public ResponseEntity<String> uploadCoverPicture(@PathVariable Long id, @RequestParam("file") MultipartFile file) {
//        String url = memberService.storeCoverPicture(id, file);
//        return ResponseEntity.ok(url);
//    }
//
//    @GetMapping("/picture/{filename:.+}")
//    public ResponseEntity<Resource> getProfilePicture(@PathVariable String filename) {
//        Resource file = fileStorageService.load(filename);
//        return ResponseEntity.ok()
//                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename() + "\"")
//                .body(file);
//    }
}
