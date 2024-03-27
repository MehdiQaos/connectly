package dev.mehdi.connectly.dto.report;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder @Getter @Setter
public class ReportRequestDto {
    private String reportReason;

    private Long reportingMemberId;

    private Long reportedPostId;
}