package dev.mehdi.connectly.dto.member;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder @Getter @Setter
public class MemberRequestDto {
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private Boolean enabled;
    private Long roleId;
}