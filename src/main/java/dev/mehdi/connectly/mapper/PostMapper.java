package dev.mehdi.connectly.mapper;

import dev.mehdi.connectly.dto.post.PostRequestDto;
import dev.mehdi.connectly.dto.post.PostResponseDto;
import dev.mehdi.connectly.dto.post.SimplePostDto;
import dev.mehdi.connectly.model.Post;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(
        componentModel = MappingConstants.ComponentModel.SPRING,
        builder = @Builder(disableBuilder = true),
        uses = {MemberMapper.class}
)
public interface PostMapper {
    Post toPost(PostRequestDto postRequestDto);

    PostResponseDto toDto(Post post);

    @Mapping(target = "numberOfComments", expression = "java(post.getComments().size())")
    @Mapping(target = "numberOfLikes", expression = "java(post.getLikedMembers().size())")
    SimplePostDto toSimpleDto(Post post);
}
