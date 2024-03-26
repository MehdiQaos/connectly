package dev.mehdi.connectly.repository;

import dev.mehdi.connectly.model.Comment;
import dev.mehdi.connectly.model.Event;
import dev.mehdi.connectly.model.Member;
import dev.mehdi.connectly.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface EventRepository extends JpaRepository<Event, Long> {
    Optional<Event> findByComment(Comment comment);
    List<Event> findAllByCommentId(Long commentId);

    Optional<Event> findByInitiatingMemberAndPost(Member member, Post post);
}
