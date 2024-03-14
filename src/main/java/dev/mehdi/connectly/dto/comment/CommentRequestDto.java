package dev.mehdi.connectly.dto.comment;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder @Getter @Setter
public class CommentRequestDto {
    private String content;

    private Long memberId;

    private Long postId;
}
