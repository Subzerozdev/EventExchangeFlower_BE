package com.eventflowerexchange.dto.request;

import com.eventflowerexchange.entity.NOTIFICATION_TYPE;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NotificationRequest {
    private NOTIFICATION_TYPE type;
    private String message;
    private String receiver;
}
