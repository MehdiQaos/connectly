package dev.mehdi.connectly.controller;

import dev.mehdi.connectly.dto.post.PostResponseDto;
import dev.mehdi.connectly.exception.ResourceNotFoundException;
import dev.mehdi.connectly.mapper.PostMapper;
import dev.mehdi.connectly.model.Post;
import dev.mehdi.connectly.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/posts")
@RequiredArgsConstructor
public class PostController {
    private final PostService postService;
    private final PostMapper postMapper;

    @GetMapping("/member/{memberId}")
    public ResponseEntity<List<PostResponseDto>> getPostsByMemberId(@PathVariable Long memberId) {
        List<PostResponseDto> posts = postService.getPostsByMemberId(memberId)
                .stream().map(postMapper::toDto).toList();
        return ResponseEntity.ok(posts);
    }

    @GetMapping("/{postId}")
    public ResponseEntity<PostResponseDto> getPostById(@PathVariable Long postId) {
        Post post = postService.findById(postId)
                .orElseThrow(() -> new ResourceNotFoundException("Post not found"));
        return ResponseEntity.ok(postMapper.toDto(post));
    }

    @GetMapping("/search")
    public ResponseEntity<List<PostResponseDto>> search(
            @RequestParam(required = false, defaultValue = "") String query
    ) {
        List<PostResponseDto> posts = postService.search(query)
                .stream().map(postMapper::toDto).toList();
        return ResponseEntity.ok(posts);
    }

    @GetMapping("followings/{memberId}")
    public ResponseEntity<List<PostResponseDto>> getPostsByFollowings(@PathVariable Long memberId) {
        List<PostResponseDto> posts = postService.getPostsByFollowings(memberId)
                .stream().map(postMapper::toDto).toList();
        return ResponseEntity.ok(posts);
    }

    @GetMapping("timeline/{memberId}")
    public ResponseEntity<List<PostResponseDto>> getTimeline(@PathVariable Long memberId) {
        List<PostResponseDto> posts = postService.getTimeline(memberId)
                .stream().map(postMapper::toDto).toList();
        return ResponseEntity.ok(posts);
    }

    @GetMapping("suggestions/{memberId}")
    public ResponseEntity<List<PostResponseDto>> getSuggestions(@PathVariable Long memberId) {
        List<PostResponseDto> posts = postService.getSuggestions(memberId)
                .stream().map(postMapper::toDto).toList();
        return ResponseEntity.ok(posts);
    }

    @PostMapping("/{memberId}/like/{postId}")
    @PreAuthorize("#memberId == authentication.principal.id")
    public void likePost(@PathVariable Long memberId, @PathVariable Long postId) {
        postService.likePost(memberId, postId);
    }

    @PostMapping("/{memberId}/unlike/{postId}")
    @PreAuthorize("#memberId == authentication.principal.id")
    public void unlikePost(@PathVariable Long memberId, @PathVariable Long postId) {
        postService.unlikePost(memberId, postId);
    }

    @PostMapping("/{memberId}")
    @PreAuthorize("#memberId == authentication.principal.id")
    public ResponseEntity<PostResponseDto> createPost(
            @PathVariable Long memberId,
            @RequestParam("content") String text,
            @RequestParam(value = "file", required = false) MultipartFile file
    ) {
        Post post = postService.newPost(memberId, text, file);
        return ResponseEntity.ok(postMapper.toDto(post));
    }

    @DeleteMapping("/{postId}")
    @PreAuthorize("hasRole('ADMIN') or @postServiceImpl.isOwner(authentication.principal.id, #postId)")
    public ResponseEntity<Boolean> deletePost(@PathVariable Long postId) {
        postService.deleteById(postId);
        return ResponseEntity.ok(true);
    }
}