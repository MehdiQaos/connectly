package dev.mehdi.connectly.model;

import jakarta.persistence.*;
import lombok.*;

@Getter @Setter @Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "report")
public class Report extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "report_reason", nullable = false, columnDefinition = "MEDIUMTEXT")
    private String reportReason;

    @ManyToOne(optional = false)
    @JoinColumn(name = "reporting_member_id", nullable = false)
    private Member reportingMember;

    @ManyToOne(optional = false)
    @JoinColumn(name = "reported_post_id", nullable = false)
    private Post reportedPost;

    @Column(name = "report_processed", nullable = false)
    private Boolean reportProcessed = false;
}