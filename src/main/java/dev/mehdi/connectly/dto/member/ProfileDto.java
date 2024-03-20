package dev.mehdi.connectly.dto.member;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder @Getter @Setter
public class ProfileDto {
    private MemberResponseDto member;
    private Integer numberOfPosts;
    private Integer numberOfFollowers;
    private Integer numberOfFollowings;
    private Integer numberOfComments;
    private Integer numberOfLikes;
}