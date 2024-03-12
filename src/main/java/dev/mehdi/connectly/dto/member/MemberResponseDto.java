package dev.mehdi.connectly.dto.member;

import dev.mehdi.connectly.dto.role.RoleDto;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class MemberResponseDto {
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private Boolean enabled;
    private RoleDto roleDto;
}
