package dev.mehdi.connectly.mapper;

import dev.mehdi.connectly.dto.role.RoleDto;
import dev.mehdi.connectly.model.Role;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(
        componentModel = MappingConstants.ComponentModel.SPRING,
        builder = @Builder(disableBuilder = true)
)
public interface RoleMapper {
    RoleDto toRoleDto(Role role);
}
