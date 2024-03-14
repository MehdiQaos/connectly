package dev.mehdi.connectly.controller;

import dev.mehdi.connectly.dto.reaction.ReactionRequestDto;
import dev.mehdi.connectly.dto.reaction.ReactionResponseDto;
import dev.mehdi.connectly.mapper.ReactionMapper;
import dev.mehdi.connectly.service.ReactionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reactions")
@RequiredArgsConstructor
public class ReactionController {
    private final ReactionService reactionService;
    private final ReactionMapper reactionMapper;

    @GetMapping("/post/{postId}")
    public ResponseEntity<List<ReactionResponseDto>> findReactionsByPostId(@PathVariable Long postId) {
        List<ReactionResponseDto> reactions = reactionService.findReactionsByPostId(postId).stream()
                .map(reactionMapper::toDto)
                .toList();
        return ResponseEntity.ok(reactions);
    }

    @PostMapping
    public ResponseEntity<ReactionResponseDto> createReaction(
            @Valid @RequestBody ReactionRequestDto dto) {
        ReactionResponseDto reaction = reactionMapper.toDto(reactionService.createReaction(dto));
        return ResponseEntity.ok(reaction);
    }

    @DeleteMapping("/{reactionId}")
    public ResponseEntity<Void> deleteReaction(@PathVariable Long reactionId) {
        reactionService.deleteReaction(reactionId);
        return ResponseEntity.noContent().build();
    }
}
