package dev.mehdi.connectly.mapper;

import dev.mehdi.connectly.dto.comment.CommentResponseDto;
import dev.mehdi.connectly.model.Comment;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(
        componentModel = MappingConstants.ComponentModel.SPRING,
        builder = @Builder(disableBuilder = true),
        uses = {MemberMapper.class}
)
public interface CommentMapper {
    @Mapping(target = "postId", source = "post.id")
    CommentResponseDto toCommentResponseDto(Comment comment);
}
