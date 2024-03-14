package dev.mehdi.connectly.service.impl;

import dev.mehdi.connectly.dto.comment.CommentRequestDto;
import dev.mehdi.connectly.exception.ResourceNotFoundException;
import dev.mehdi.connectly.model.Comment;
import dev.mehdi.connectly.model.Member;
import dev.mehdi.connectly.model.Post;
import dev.mehdi.connectly.repository.CommentRepository;
import dev.mehdi.connectly.service.CommentService;
import dev.mehdi.connectly.service.MemberService;
import dev.mehdi.connectly.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {
    private final CommentRepository commentRepository;
    private final MemberService memberService;
    private final PostService postService;

    @Override
    public Comment createComment(CommentRequestDto dto) {
        Member member = memberService.findById(dto.getMemberId()).orElseThrow(
                () -> new ResourceNotFoundException("Member not found")
        );
        Post post = postService.findById(dto.getPostId()).orElseThrow(
                () -> new ResourceNotFoundException("Post not found")
        );
        Comment comment = Comment.builder()
                .member(member).post(post).build();
        return commentRepository.save(comment);
    }

    @Override
    public List<Comment> findCommentsByPostId(Long postId) {
        Post post = postService.findById(postId).orElseThrow(
                () -> new ResourceNotFoundException("Post not found")
        );
        return post.getComments().stream().toList();
    }
}
