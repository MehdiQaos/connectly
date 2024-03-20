package dev.mehdi.connectly.mapper;

import dev.mehdi.connectly.dto.member.MemberRequestDto;
import dev.mehdi.connectly.dto.member.FullMemberDto;
import dev.mehdi.connectly.dto.member.MemberResponseDto;
import dev.mehdi.connectly.dto.member.ProfileDto;
import dev.mehdi.connectly.model.Member;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(
        componentModel = MappingConstants.ComponentModel.SPRING,
        builder = @Builder(disableBuilder = true),
        uses = {RoleMapper.class}
)
public interface MemberMapper {

    Member requestToMember(MemberRequestDto memberRequestDto);

    @Mapping(target = "roleDto", source = "role")
    FullMemberDto toFullDto(Member member);

    MemberResponseDto toDto(Member member);

    @Mapping(target = "numberOfFollowers", expression = "java(member.getFollowers().size())")
    @Mapping(target = "numberOfFollowings", expression = "java(member.getFollowings().size())")
    @Mapping(target = "numberOfPosts", expression = "java(member.getPosts().size())")
    @Mapping(target = "numberOfLikes", expression = "java(member.getLikedPosts().size())")
    @Mapping(target = "numberOfComments", expression = "java(member.getComments().size())")
    ProfileDto toProfileDto(Member member);

//    Member editProfileToMember(ProfileDto profileDto, Member member);
}
