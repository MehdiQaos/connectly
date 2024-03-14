package dev.mehdi.connectly.service.impl;

import dev.mehdi.connectly.dto.reaction.ReactionRequestDto;
import dev.mehdi.connectly.exception.ResourceNotFoundException;
import dev.mehdi.connectly.model.Member;
import dev.mehdi.connectly.model.Post;
import dev.mehdi.connectly.model.Reaction;
import dev.mehdi.connectly.model.enums.ReactionType;
import dev.mehdi.connectly.repository.ReactionRepository;
import dev.mehdi.connectly.service.MemberService;
import dev.mehdi.connectly.service.PostService;
import dev.mehdi.connectly.service.ReactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReactionServiceImpl implements ReactionService {
    private final ReactionRepository reactionRepository;
    private final PostService postService;
    private final MemberService memberService;

    @Override
    public List<Reaction> findReactionsByPostId(Long postId) {
        Post post = postService.findById(postId).orElseThrow(
                () -> new ResourceNotFoundException("Post not found")
        );
        return post.getReactions().stream().toList();
    }

    @Override
    public Reaction createReaction(ReactionRequestDto dto) {
        ReactionType type = ReactionType.values()[dto.getReactionTypeId()];
        Post post = postService.findById(dto.getPostId()).orElseThrow(
                () -> new ResourceNotFoundException("Post not found")
        );
        Member member = memberService.findById(dto.getMemberId()).orElseThrow(
                () -> new ResourceNotFoundException("Member not found")
        );
        Reaction reaction = Reaction.builder().post(post).member(member)
                .type(type).build();
        return reactionRepository.save(reaction);
    }

    @Override
    public void deleteReaction(Long reactionId) {
        if (!reactionRepository.existsById(reactionId)) {
            throw new ResourceNotFoundException("Reaction not found");
        }
        reactionRepository.deleteById(reactionId);
    }
}
