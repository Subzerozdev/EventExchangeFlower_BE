package com.eventflowerexchange.api;

import com.eventflowerexchange.dto.request.NotificationRequest;
import com.eventflowerexchange.entity.UserNotification;
import com.eventflowerexchange.service.JwtService;
import com.eventflowerexchange.service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class NotificationAPI {
    private final NotificationService notificationService;
    private final JwtService jwtService;

    @PostMapping("/admin/notification")
    public ResponseEntity<Object> createNotification(
            @RequestBody NotificationRequest notificationRequest
    ) {
        notificationService.createNotification(notificationRequest);
        return new ResponseEntity<>("Create successfully", HttpStatus.OK);
    }

    @GetMapping("/notification")
    public ResponseEntity<Object> getUserNotification(
            @RequestHeader("Authorization") String jwt
    ) {
        String userID = jwtService.getUserIdFromJwtToken(jwt);
        List<UserNotification> userNotifications = notificationService.getUserNotifications(userID);
        return new ResponseEntity<>(userNotifications, HttpStatus.OK);
    }
}
