package com.eventflowerexchange.service.impl;

import com.eventflowerexchange.dto.request.NotificationRequest;
import com.eventflowerexchange.entity.NOTIFICATION_TYPE;
import com.eventflowerexchange.entity.User;
import com.eventflowerexchange.entity.UserNotification;
import com.eventflowerexchange.repository.NotificationRepository;
import com.eventflowerexchange.service.NotificationService;
import com.eventflowerexchange.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class NotificationServiceImpl implements NotificationService {
    private final NotificationRepository notificationRepository;
    private final UserService userService;

    @Override
    public void createNotification(User receiverUser, String sender,
                                   NOTIFICATION_TYPE type, String message) {
        UserNotification userNotification = UserNotification.builder()
                .receiverUser(receiverUser)
                .sender(sender)
                .createDate(LocalDateTime.now())
                .notificationType(type)
                .message(message)
                .build();
        notificationRepository.save(userNotification);
    }

    @Override
    public List<UserNotification> getUserNotifications(String userID) {
        return notificationRepository.getUserNotificationByReceiverUserId(userID);
    }

    @Override
    public List<UserNotification> getAdminNotifications(String sender) {
        return notificationRepository.getUserNotificationBySender(sender);
    }
}
