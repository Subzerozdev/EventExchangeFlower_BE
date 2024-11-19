package com.eventflowerexchange.repository;

import com.eventflowerexchange.entity.UserNotification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NotificationRepository extends JpaRepository<UserNotification, Long> {
    UserNotification getUserNotificationByIdAndIsReadFalse(Long notificationID);
    List<UserNotification> getUserNotificationBySender(String sender);
    List<UserNotification> getUserNotificationByReceiverUserIdAndIsReadFalseOrderByCreateDateDesc(String userID);
}
