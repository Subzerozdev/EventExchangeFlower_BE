package com.eventflowerexchange.service;

import com.eventflowerexchange.dto.request.ApplicationRequestDTO;
import com.eventflowerexchange.entity.APPLICATION_TYPE;
import com.eventflowerexchange.entity.Application;
import com.eventflowerexchange.entity.User;

import java.util.List;

public interface ApplicationService {
    void createReport(ApplicationRequestDTO applicationRequestDTO, User user, APPLICATION_TYPE applicationType);
    User solveReport(int reportId, boolean status);
    List<Application> getUserReport();
}
