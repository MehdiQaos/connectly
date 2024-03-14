package dev.mehdi.connectly.mapper;

import dev.mehdi.connectly.dto.member.MemberRequestDto;
import dev.mehdi.connectly.dto.member.MemberResponseDto;
import dev.mehdi.connectly.dto.member.SimpleMember;
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

    Member toMember(MemberRequestDto memberRequestDto);

    @Mapping(target = "roleDto", source = "role")
    MemberResponseDto toMemberResponseDto(Member member);

    SimpleMember toSimpleMember(Member member);
}
