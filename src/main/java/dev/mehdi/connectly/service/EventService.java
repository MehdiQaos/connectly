package dev.mehdi.connectly.service;

import dev.mehdi.connectly.model.Comment;
import dev.mehdi.connectly.model.Event;
import dev.mehdi.connectly.model.Member;
import dev.mehdi.connectly.model.Post;

import java.util.List;
import java.util.Optional;

public interface EventService {
    Event save(Event event);

    void delete(Event event);

    Optional<Event> findByComment(Comment comment);

    List<Event> findAllByCommentId(Long commentId);

    Optional<Event> findLikeEvent(Member member, Post post);

    List<Event> findByPostId(Long postId);

    void deleteById(Long eventId);
}
