package dev.mehdi.connectly.dto.post;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder @Getter @Setter
public class PostRequestDto {
    private String content;

    private Long memberId;
}