package dev.mehdi.connectly.dto.comment;

import dev.mehdi.connectly.dto.member.MemberResponseDto;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder @Getter @Setter
public class CommentResponseDto {
    private Long id;

    private String content;

    private MemberResponseDto member;

    private Long postId;
}
