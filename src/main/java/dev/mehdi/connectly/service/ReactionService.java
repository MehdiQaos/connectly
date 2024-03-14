package dev.mehdi.connectly.service;

import dev.mehdi.connectly.dto.reaction.ReactionRequestDto;
import dev.mehdi.connectly.model.Reaction;

import java.util.List;

public interface ReactionService {
    List<Reaction> findReactionsByPostId(Long postId);

    Reaction createReaction(ReactionRequestDto dto);

    void deleteReaction(Long reactionId);
}
