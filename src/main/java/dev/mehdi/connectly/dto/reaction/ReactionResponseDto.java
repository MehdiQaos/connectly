package dev.mehdi.connectly.dto.reaction;

import dev.mehdi.connectly.dto.member.MemberResponseDto;
import dev.mehdi.connectly.dto.member.SimpleMember;
import dev.mehdi.connectly.model.enums.ReactionType;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder @Getter @Setter
public class ReactionResponseDto {
    private Long id;

    private ReactionType type;

    private SimpleMember member;
}
