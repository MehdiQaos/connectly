package dev.mehdi.connectly.service.impl;

import dev.mehdi.connectly.dto.comment.CommentRequestDto;
import dev.mehdi.connectly.exception.ResourceNotFoundException;
import dev.mehdi.connectly.model.Comment;
import dev.mehdi.connectly.model.Event;
import dev.mehdi.connectly.model.Member;
import dev.mehdi.connectly.model.Post;
import dev.mehdi.connectly.model.enums.EventType;
import dev.mehdi.connectly.repository.CommentRepository;
import dev.mehdi.connectly.service.CommentService;
import dev.mehdi.connectly.service.EventService;
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
    private final EventService eventService;

    @Override
    public Comment createComment(CommentRequestDto dto) {
        Member member = memberService.findById(dto.getMemberId()).orElseThrow(
                () -> new ResourceNotFoundException("Member not found")
        );
        Post post = postService.findById(dto.getPostId()).orElseThrow(
                () -> new ResourceNotFoundException("Post not found")
        );
        Comment comment = Comment.builder()
                .content(dto.getContent()).member(member).post(post).build();
        if (!member.equals(post.getMember())) {
            Event newEvent = new Event(null, EventType.COMMENT, post, comment, member, post.getMember());
            post.getMember().addEvent(newEvent);
        }
        return commentRepository.save(comment);
    }

    @Override
    public List<Comment> findCommentsByPostId(Long postId) {
        Post post = postService.findById(postId).orElseThrow(
                () -> new ResourceNotFoundException("Post not found")
        );
        return post.getComments().stream().toList();
    }

    @Override
    public void deleteComment(Long commentId) {
        Comment comment = commentRepository.findById(commentId).
                orElseThrow(() -> new ResourceNotFoundException("Comment not found"));
        eventService.findByComment(comment).ifPresent(eventService::delete);
        commentRepository.delete(comment);
    }
}
