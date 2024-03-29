package dev.mehdi.connectly.dto.member;

import lombok.*;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Builder @Getter @Setter
public class MemberResponseDto {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private LocalDate birthDate;
    private String bio;
    private String location;
    private String profession;
    private String profilePictureLocation;
}
