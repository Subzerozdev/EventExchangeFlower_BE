package com.eventflowerexchange.service;

import com.eventflowerexchange.dto.request.NotificationRequest;
import com.eventflowerexchange.entity.NOTIFICATION_TYPE;
import com.eventflowerexchange.entity.User;
import com.eventflowerexchange.entity.UserNotification;

import java.util.List;

public interface NotificationService {
    void createNotification(User receiverUser, String sender,
                            NOTIFICATION_TYPE type, String message);
    List<UserNotification> getUserNotifications(String userID);
    List<UserNotification> getAdminNotifications(String sender);
}
