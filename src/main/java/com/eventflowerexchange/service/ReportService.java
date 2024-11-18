package com.eventflowerexchange.service;

import com.eventflowerexchange.dto.request.ReportRequestDTO;
import com.eventflowerexchange.entity.Report;
import com.eventflowerexchange.entity.User;

import java.util.List;

public interface ReportService {
    void createReport(ReportRequestDTO reportRequestDTO, User user);
    User solveReport(int reportId, boolean status);
    List<Report> getUserReport();
}
