package dev.mehdi.connectly.dto.report;

import dev.mehdi.connectly.dto.member.MemberResponseDto;
import dev.mehdi.connectly.dto.post.PostResponseDto;
import dev.mehdi.connectly.dto.post.SimplePostDto;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder @Getter @Setter
public class ReportResponseDto {
    private Long id;

    private String reportReason;

    private MemberResponseDto reportingMember;

    private SimplePostDto reportedPost;

    private Boolean reportProcessed;
}