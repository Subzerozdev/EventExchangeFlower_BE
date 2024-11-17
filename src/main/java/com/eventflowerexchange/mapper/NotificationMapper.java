package com.eventflowerexchange.mapper;

import com.eventflowerexchange.dto.response.NotificationResponse;
import com.eventflowerexchange.entity.UserNotification;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface NotificationMapper {
    NotificationResponse toNotificationResponse(UserNotification notification);
}
