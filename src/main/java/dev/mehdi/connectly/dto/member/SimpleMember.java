package dev.mehdi.connectly.dto.member;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder @Getter @Setter
public class SimpleMember {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
}
