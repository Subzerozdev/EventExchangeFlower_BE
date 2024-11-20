package com.eventflowerexchange.repository;

import com.eventflowerexchange.entity.Application;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ApplicationRepository extends JpaRepository<Application, Integer> {
    Application getReportById(int id);
}
