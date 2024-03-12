package dev.mehdi.connectly.dto.member;

import jakarta.validation.constraints.*;
import lombok.*;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Builder @Getter @Setter
public class MemberRequestDto {
    @NotBlank(message = "First name is required")
    @Size(max = 100, message = "First name cannot be longer than 100 characters")
    private String firstName;

    @NotBlank(message = "Last name is required")
    @Size(max = 100, message = "Last name cannot be longer than 100 characters")
    private String lastName;

    @NotBlank(message = "Email is required")
    private String email;

    @NotBlank(message = "Password is required")
    @Size(min = 6, message = "Password must be at least 6 characters long")
    private String password;

    @Past(message = "Birth date must be in the past")
    private LocalDate birthDate;

    @NotNull(message = "Enabled status is required")
    private Boolean enabled;

    @NotNull(message = "Role ID is required")
    @Min(value = 1, message = "Role ID must be between 1 and 3")
    @Max(value = 3, message = "Role ID must be between 1 and 3")
    private Long roleId;
}