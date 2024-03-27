package dev.mehdi.connectly.service;

import dev.mehdi.connectly.dto.report.ReportRequestDto;
import dev.mehdi.connectly.model.Report;

import java.util.List;

public interface ReportService {
    Report newReport(ReportRequestDto reportRequestDto);

    List<Report> getAllReports();

    List<Report> getNotProcessedReports();

    void deleteReport(Long id);
}
