package dev.mehdi.connectly.controller;

import dev.mehdi.connectly.dto.event.EventResponseDto;
import dev.mehdi.connectly.exception.ResourceNotFoundException;
import dev.mehdi.connectly.mapper.EventMapper;
import dev.mehdi.connectly.model.Event;
import dev.mehdi.connectly.model.Member;
import dev.mehdi.connectly.service.EventService;
import dev.mehdi.connectly.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/events")
@RequiredArgsConstructor
public class EventController {
    private final EventService eventService;
    private final MemberService memberService;
    private final EventMapper eventMapper;

    @GetMapping("/member/{memberId}")
    public ResponseEntity<List<EventResponseDto>> getMemberEvents(@PathVariable Long memberId) {
        List<EventResponseDto> eventsDto = memberService.findById(memberId)
                .map(Member::getNewEvents)
                .map(events1 -> events1.stream().map(eventMapper::toDto).toList())
                .orElseThrow(
                        () -> new ResourceNotFoundException("Member not found")
                );
        return ResponseEntity.ok(eventsDto);
    }

    @DeleteMapping("/{eventId}")
    public ResponseEntity<Void> deleteEvent(@PathVariable Long eventId) {
        eventService.deleteById(eventId);
        return ResponseEntity.ok().build();
    }
}
