package com.eventflowerexchange.service;

import com.eventflowerexchange.dto.request.ReportRequestDTO;
import com.eventflowerexchange.entity.User;

public interface ReportService {
    void createReport(ReportRequestDTO reportRequestDTO, User user);
    void solveReport(int reportId);
}
