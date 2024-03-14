package dev.mehdi.connectly.controller;

import dev.mehdi.connectly.dto.comment.CommentRequestDto;
import dev.mehdi.connectly.dto.comment.CommentResponseDto;
import dev.mehdi.connectly.mapper.CommentMapper;
import dev.mehdi.connectly.model.Comment;
import dev.mehdi.connectly.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/comments")
@RequiredArgsConstructor
public class CommentController {
    private final CommentService commentService;
    private final CommentMapper commentMapper;

    @PostMapping
    public ResponseEntity<CommentResponseDto> createComment(@RequestBody CommentRequestDto commentRequestDto) {
        Comment newComment = commentService.createComment(commentRequestDto);
        CommentResponseDto dto = commentMapper.toCommentResponseDto(newComment);
        return new ResponseEntity<>(dto, HttpStatus.CREATED);
    }

    @GetMapping("/post/{postId}")
    public ResponseEntity<List<CommentResponseDto>> findCommentsByPostId(@PathVariable Long postId) {
        List<CommentResponseDto> comments = commentService.findCommentsByPostId(postId).stream()
                .map(commentMapper::toCommentResponseDto)
                .toList();
        return ResponseEntity.ok(comments);
    }
}
