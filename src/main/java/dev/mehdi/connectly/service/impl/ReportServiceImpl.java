package dev.mehdi.connectly.service.impl;

import dev.mehdi.connectly.dto.report.ReportRequestDto;
import dev.mehdi.connectly.exception.ResourceNotFoundException;
import dev.mehdi.connectly.model.Member;
import dev.mehdi.connectly.model.Post;
import dev.mehdi.connectly.model.Report;
import dev.mehdi.connectly.repository.ReportRepository;
import dev.mehdi.connectly.service.MemberService;
import dev.mehdi.connectly.service.PostService;
import dev.mehdi.connectly.service.ReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReportServiceImpl implements ReportService {
    private final ReportRepository reportRepository;
    private final MemberService memberService;
    private final PostService postService;

    @Override
    public Report newReport(ReportRequestDto reportRequestDto) {
        Member member = memberService.findById(reportRequestDto.getReportingMemberId())
                .orElseThrow(() -> new ResourceNotFoundException("Member not found"));
        Post post = postService.findById(reportRequestDto.getReportedPostId())
                .orElseThrow(() -> new ResourceNotFoundException("Post not found"));

        Report report = Report.builder()
                .reportingMember(member)
                .reportedPost(post)
                .reportReason(reportRequestDto.getReportReason())
                .reportProcessed(false)
                .build();
        return reportRepository.save(report);
    }

    @Override
    public List<Report> getAllReports() {
        return reportRepository.findAll();
    }

    @Override
    public List<Report> getNotProcessedReports() {
        return reportRepository.findAllByReportProcessed(false);
    }

    @Override
    public void deleteReport(Long id) {
        Report report = reportRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Report not found"));
        report.getReportedPost().removeReport(report);
        report.getReportingMember().removeReport(report);
        reportRepository.delete(report);
    }
}
