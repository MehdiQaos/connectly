package dev.mehdi.connectly.dto.post;

import dev.mehdi.connectly.dto.member.MemberResponseDto;


public class PostUserDto {
    private Long id;

    private String content;

    private String imageLocation;

    private MemberResponseDto member;

    private boolean isLiked;

    private int likesCount;
}
