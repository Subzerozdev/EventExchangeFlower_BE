package com.eventflowerexchange.dto.response;

import com.eventflowerexchange.entity.NOTIFICATION_TYPE;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class NotificationResponse {
    private String message;
    private LocalDateTime createDate;
    private NOTIFICATION_TYPE notificationType;
    private String emailReceiver;
}
