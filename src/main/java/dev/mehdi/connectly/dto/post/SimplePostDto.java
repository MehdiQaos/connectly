package dev.mehdi.connectly.dto.post;

import dev.mehdi.connectly.dto.member.MemberResponseDto;
import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder @Getter @Setter
public class SimplePostDto {
    private Long id;

    private String content;

    private String imageLocation;

    private MemberResponseDto member;

    private int numberOfLikes;

    private int numberOfComments;
}
