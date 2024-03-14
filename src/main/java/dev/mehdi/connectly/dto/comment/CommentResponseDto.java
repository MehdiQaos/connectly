package dev.mehdi.connectly.dto.comment;

import dev.mehdi.connectly.dto.member.SimpleMember;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder @Getter @Setter
public class CommentResponseDto {
    private Long id;

    private String content;

    private SimpleMember member;

    private Long postId;
}
