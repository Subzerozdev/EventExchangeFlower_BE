package com.eventflowerexchange.service.impl;

import com.eventflowerexchange.dto.request.ApplicationRequestDTO;
import com.eventflowerexchange.entity.APPLICATION_STATUS;
import com.eventflowerexchange.entity.APPLICATION_TYPE;
import com.eventflowerexchange.entity.Application;
import com.eventflowerexchange.entity.User;
import com.eventflowerexchange.repository.ApplicationRepository;
import com.eventflowerexchange.service.ApplicationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ApplicationServiceImpl implements ApplicationService {
    private final ApplicationRepository applicationRepository;

    @Override
    public void createReport(ApplicationRequestDTO applicationRequestDTO, Long orderID, User user, APPLICATION_TYPE applicationType) {
        Application application = Application.builder()
                .content(applicationRequestDTO.getContent())
                .problem(applicationRequestDTO.getProblem())
                .orderID(orderID)
                .user(user)
                .status(APPLICATION_STATUS.PROCESSING)
                .type(applicationType)
                .build();
        applicationRepository.save(application);
    }

    @Override
    public User solveReport(int reportId, boolean status) {
        Application application = applicationRepository.getReportById(reportId);
        if (status) {
            application.setStatus(APPLICATION_STATUS.COMPLETED);
        } else {
            application.setStatus(APPLICATION_STATUS.REJECTED);
        }
        applicationRepository.save(application);
        return application.getUser();
    }

    @Override
    public List<Application> getUserReport() {
        return applicationRepository.findAll();
    }

    @Override
    public Application getReport(int reportId) {
        return applicationRepository.getReportById(reportId);
    }

}
