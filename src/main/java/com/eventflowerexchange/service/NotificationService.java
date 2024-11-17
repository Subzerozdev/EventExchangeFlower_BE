package com.eventflowerexchange.service;

import com.eventflowerexchange.dto.request.NotificationRequest;
import com.eventflowerexchange.entity.UserNotification;

import java.util.List;

public interface NotificationService {
    void createNotification(NotificationRequest notificationRequest);
    List<UserNotification> getUserNotifications(String userID);
}
