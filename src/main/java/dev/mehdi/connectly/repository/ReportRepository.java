package dev.mehdi.connectly.repository;

import dev.mehdi.connectly.model.Report;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReportRepository extends JpaRepository<Report, Long> {
    List<Report> findAllByReportProcessed(boolean b);
}
