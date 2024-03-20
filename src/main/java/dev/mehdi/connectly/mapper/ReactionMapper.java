//package dev.mehdi.connectly.mapper;
//
//import dev.mehdi.connectly.dto.reaction.ReactionResponseDto;
//import org.mapstruct.Builder;
//import org.mapstruct.Mapper;
//import org.mapstruct.MappingConstants;
//
//import java.util.List;
//import java.util.Set;

//@Mapper(
//        componentModel = MappingConstants.ComponentModel.SPRING,
//        builder = @Builder(disableBuilder = true),
//        uses = {MemberMapper.class}
//)
//public interface ReactionMapper {
//    ReactionResponseDto toDto(Reaction reaction);
//
//    default List<ReactionResponseDto> toDtoList(Set<Reaction> reactions) {
//        return reactions.stream().map(this::toDto).toList();
//    }
//}
