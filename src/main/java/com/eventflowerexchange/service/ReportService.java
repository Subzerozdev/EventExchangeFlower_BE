package com.eventflowerexchange.service;

import com.eventflowerexchange.dto.request.ReportRequestDTO;
import com.eventflowerexchange.entity.Report;
import com.eventflowerexchange.entity.User;

import java.util.List;

public interface ReportService {
    void createReport(ReportRequestDTO reportRequestDTO, User user);
    void solveReport(int reportId);
    List<Report> getUserReport();
}
