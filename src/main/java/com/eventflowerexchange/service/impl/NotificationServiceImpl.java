package com.eventflowerexchange.service.impl;

import com.eventflowerexchange.dto.request.NotificationRequest;
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
    public void createNotification(NotificationRequest notificationRequest) {
        UserNotification userNotification = UserNotification.builder()
                .user(userService.findUserById(notificationRequest.getReceiver()))
                .createDate(LocalDateTime.now())
                .notificationType(notificationRequest.getType())
                .message(notificationRequest.getMessage())
                .build();
        notificationRepository.save(userNotification);
    }

    @Override
    public List<UserNotification> getUserNotifications(String userID) {
        return notificationRepository.getUserNotificationByUserId(userID);
    }
}
