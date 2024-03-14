package dev.mehdi.connectly.dto.reaction;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder @Getter @Setter
public class ReactionRequestDto {
    @NotNull(message = "Reaction type ID cannot be null")
    @Min(value = 0, message = "Reaction type ID must be greater than 0")
    private int reactionTypeId;

    @NotNull(message = "Member ID cannot be null")
    @Min(value = 1, message = "Member ID must be greater than 0")
    private Long memberId;

    @NotNull(message = "Post ID cannot be null")
    @Min(value = 1, message = "Post ID must be greater than 0")
    private Long postId;
}