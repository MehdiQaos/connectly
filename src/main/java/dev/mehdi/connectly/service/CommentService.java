package dev.mehdi.connectly.service;

import dev.mehdi.connectly.dto.comment.CommentRequestDto;
import dev.mehdi.connectly.model.Comment;

import java.util.List;

public interface CommentService {
    Comment createComment(CommentRequestDto commentRequestDto);

    List<Comment> findCommentsByPostId(Long postId);
}
