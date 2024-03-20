package dev.mehdi.connectly.dto.member;

import dev.mehdi.connectly.dto.role.RoleDto;
import lombok.*;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class FullMemberDto {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private LocalDate birthDate;
    private String bio;
    private String location;
    private String profession;
    private Boolean enabled;
    private RoleDto roleDto;
    private String profilePictureLocation;
}
