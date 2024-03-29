package dev.mehdi.connectly.controller;

import dev.mehdi.connectly.dto.report.ReportRequestDto;
import dev.mehdi.connectly.dto.report.ReportResponseDto;
import dev.mehdi.connectly.mapper.ReportMapper;
import dev.mehdi.connectly.model.Report;
import dev.mehdi.connectly.service.ReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reports")
@RequiredArgsConstructor
public class ReportController {
    private final ReportService reportService;
    private final ReportMapper reportMapper;

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public ResponseEntity<Void> reportPost(@RequestBody ReportRequestDto reportRequestDto) {
        Report report = reportService.newReport(reportRequestDto);
        return ResponseEntity.ok().build();
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<ReportResponseDto>> getAllReports() {
        List<ReportResponseDto> reports = reportService.getAllReports().stream()
                .map(reportMapper::toDto).toList();
        return ResponseEntity.ok(reports);
    }

    @GetMapping("/not-processed")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<ReportResponseDto>> getNotProcessedReports() {
        List<ReportResponseDto> reports = reportService.getNotProcessedReports().stream()
                .map(reportMapper::toDto).toList();
        return ResponseEntity.ok(reports);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteReport(@PathVariable Long id) {
        reportService.deleteReport(id);
        return ResponseEntity.ok().build();
    }
}
