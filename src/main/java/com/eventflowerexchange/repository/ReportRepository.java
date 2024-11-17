package com.eventflowerexchange.repository;

import com.eventflowerexchange.entity.Report;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReportRepository extends JpaRepository<Report, Integer> {
    Report getReportById(int id);
}
