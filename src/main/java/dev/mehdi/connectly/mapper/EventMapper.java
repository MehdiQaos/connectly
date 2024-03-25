package dev.mehdi.connectly.mapper;

import dev.mehdi.connectly.dto.event.EventResponseDto;
import dev.mehdi.connectly.model.Event;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(
        componentModel = MappingConstants.ComponentModel.SPRING,
        builder = @Builder(disableBuilder = true),
        uses = {MemberMapper.class, PostMapper.class, CommentMapper.class}
)
public interface EventMapper {
    EventResponseDto toDto(Event event);
}
