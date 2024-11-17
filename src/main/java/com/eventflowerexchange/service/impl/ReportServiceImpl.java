package com.eventflowerexchange.service.impl;

import com.eventflowerexchange.dto.request.ReportRequestDTO;
import com.eventflowerexchange.entity.REPORT_STATUS;
import com.eventflowerexchange.entity.Report;
import com.eventflowerexchange.entity.User;
import com.eventflowerexchange.repository.ReportRepository;
import com.eventflowerexchange.service.OrderService;
import com.eventflowerexchange.service.ReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReportServiceImpl implements ReportService {
    private final ReportRepository reportRepository;
    private final OrderService orderService;

    @Override
    public void createReport(ReportRequestDTO reportRequestDTO, User user) {
        Report report = Report.builder()
                .content(reportRequestDTO.getContent())
                .problem(reportRequestDTO.getProblem())
                .order(orderService.getOrderById(reportRequestDTO.getOrderId()))
                .status(REPORT_STATUS.PROCESSING)
                .build();
        reportRepository.save(report);
    }

    @Override
    public void solveReport(int reportId) {
        Report report = reportRepository.getReportById(reportId);
        report.setStatus(REPORT_STATUS.COMPLETED);
        reportRepository.save(report);
    }
}
