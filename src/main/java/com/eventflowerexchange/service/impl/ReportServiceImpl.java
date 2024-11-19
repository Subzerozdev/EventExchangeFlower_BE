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

import java.util.List;

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
                .orderID(reportRequestDTO.getOrderId())
                .user(user)
                .status(REPORT_STATUS.PROCESSING)
                .build();
        reportRepository.save(report);
    }

    @Override
    public User solveReport(int reportId, boolean status) {
        Report report = reportRepository.getReportById(reportId);
        if (status) {
            report.setStatus(REPORT_STATUS.COMPLETED);
        } else {
            report.setStatus(REPORT_STATUS.REJECTED);
        }
        reportRepository.save(report);
        return report.getUser();
    }

    @Override
    public List<Report> getUserReport() {
        return reportRepository.findAll();
    }
}
