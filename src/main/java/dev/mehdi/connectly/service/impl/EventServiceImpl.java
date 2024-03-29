package dev.mehdi.connectly.service.impl;

import dev.mehdi.connectly.exception.ResourceNotFoundException;
import dev.mehdi.connectly.model.Comment;
import dev.mehdi.connectly.model.Event;
import dev.mehdi.connectly.model.Member;
import dev.mehdi.connectly.model.Post;
import dev.mehdi.connectly.repository.EventRepository;
import dev.mehdi.connectly.service.EventService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class EventServiceImpl implements EventService {
    private final EventRepository eventRepository;

    @Override
    public Event save(Event event) {
        return eventRepository.save(event);
    }

    @Override
    public void delete(Event event) {
        event.getAffectedMember().removeEvent(event);
        eventRepository.delete(event);
    }

    @Override
    public Optional<Event> findByComment(Comment comment) {
        return eventRepository.findByComment(comment);
    }

    @Override
    public List<Event> findAllByCommentId(Long commentId) {
        return eventRepository.findAllByCommentId(commentId);
    }

    @Override
    public Optional<Event> findLikeEvent(Member member, Post post) {
        return eventRepository.findByInitiatingMemberAndPost(member, post);
    }

    @Override
    public List<Event> findByPostId(Long postId) {
        return eventRepository.findAllByPostId(postId);
    }

    @Override
    public void deleteById(Long eventId) {
        Event event = eventRepository.findById(eventId).orElseThrow(
                () -> new ResourceNotFoundException("Event not found")
        );
        event.getAffectedMember().removeEvent(event);
        eventRepository.delete(event);
    }

    public boolean isOwner(Long memberId, Long eventId) {
        Event event = eventRepository.findById(eventId).orElseThrow(
                () -> new ResourceNotFoundException("Event not found")
        );
        return event.getAffectedMember().getId().equals(memberId);
    }
}
