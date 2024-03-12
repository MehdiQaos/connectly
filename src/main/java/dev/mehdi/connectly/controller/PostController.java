package dev.mehdi.connectly.controller;

import dev.mehdi.connectly.dto.post.PostResponseDto;
import dev.mehdi.connectly.dto.post.PostRequestDto;
import dev.mehdi.connectly.mapper.PostMapper;
import dev.mehdi.connectly.model.Post;
import dev.mehdi.connectly.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/posts")
@RequiredArgsConstructor
public class PostController {
    private final PostService postService;
    private final PostMapper postMapper;

    @PostMapping
    public ResponseEntity<PostResponseDto> createPost(@RequestBody PostRequestDto postRequestDto) {
        Post createdPost = postService.createPost(postRequestDto);
        PostResponseDto postResponseDto = postMapper.toDto(createdPost);
        return new ResponseEntity<>(postResponseDto, HttpStatus.CREATED);
    }

    @GetMapping("/{memberId}")
    public ResponseEntity<List<PostResponseDto>> getPostsByMemberId(@PathVariable Long memberId) {
        List<PostResponseDto> posts = postService.getPostsByMemberId(memberId)
                .stream().map(postMapper::toDto).toList();
        return ResponseEntity.ok(posts);
    }
}