package dev.mehdi.connectly.service.impl;

import dev.mehdi.connectly.model.Comment;
import dev.mehdi.connectly.model.Event;
import dev.mehdi.connectly.model.Member;
import dev.mehdi.connectly.model.Post;
import dev.mehdi.connectly.repository.EventRepository;
import dev.mehdi.connectly.service.EventService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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
        eventRepository.delete(event);
    }

    @Override
    public Optional<Event> findByComment(Comment comment) {
        return eventRepository.findByComment(comment);
    }

    @Override
    public Optional<Event> findLikeEvent(Member member, Post post) {
        return eventRepository.findByInitiatingMemberAndPost(member, post);
    }
}
