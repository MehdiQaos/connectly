package dev.mehdi.connectly.dto.post;

import dev.mehdi.connectly.dto.member.MemberResponseDto;
import dev.mehdi.connectly.dto.reaction.ReactionResponseDto;
import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder @Getter @Setter
public class PostResponseDto {
    private Long id;

    private String content;

    private Boolean isPublic;

    private MemberResponseDto member;

    private List<ReactionResponseDto> reactions;
}