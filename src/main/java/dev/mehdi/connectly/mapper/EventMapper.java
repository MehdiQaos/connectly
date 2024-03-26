package dev.mehdi.connectly.mapper;

import dev.mehdi.connectly.dto.event.EventResponseDto;
import dev.mehdi.connectly.model.Event;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(
        componentModel = MappingConstants.ComponentModel.SPRING,
        builder = @Builder(disableBuilder = true),
        uses = {MemberMapper.class, CommentMapper.class}
)
public interface EventMapper {
    @Mapping(target = "postId", source = "post.id")
    @Mapping(target = "time", source = "createdAt")
    EventResponseDto toDto(Event event);
}