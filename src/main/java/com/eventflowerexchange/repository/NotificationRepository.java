package com.eventflowerexchange.repository;

import com.eventflowerexchange.entity.UserNotification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NotificationRepository extends JpaRepository<UserNotification, Integer> {
    List<UserNotification> getUserNotificationByUserId(String userId);
}
