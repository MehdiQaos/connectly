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

    Optional<Event> findLikeEvent(Member member, Post post);

    List<Event> getMemberEvents(Long memberId);

    void deleteById(Long eventId);
}
