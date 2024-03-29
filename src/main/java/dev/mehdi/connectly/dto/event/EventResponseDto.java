package dev.mehdi.connectly.dto.event;

import dev.mehdi.connectly.dto.comment.CommentResponseDto;
import dev.mehdi.connectly.dto.member.MemberResponseDto;
import dev.mehdi.connectly.dto.post.PostResponseDto;
import dev.mehdi.connectly.model.enums.EventType;
import lombok.*;

import java.time.Instant;

@AllArgsConstructor
@NoArgsConstructor
@Builder @Getter @Setter
public class EventResponseDto {
    private Long id;

    private EventType eventType;

    private Long postId;

    private CommentResponseDto comment;

    private MemberResponseDto initiatingMember;

    private MemberResponseDto affectedMember;

    private Instant time;
}
