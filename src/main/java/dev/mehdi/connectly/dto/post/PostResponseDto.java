package dev.mehdi.connectly.dto.post;

import dev.mehdi.connectly.dto.member.MemberResponseDto;
import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder @Getter @Setter
public class PostResponseDto {
    private Long id;

    private String content;

    private String imageLocation;

    private MemberResponseDto member;

    private List<MemberResponseDto> likedMembers;
}