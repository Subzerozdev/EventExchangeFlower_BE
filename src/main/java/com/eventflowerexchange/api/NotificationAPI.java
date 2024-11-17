package com.eventflowerexchange.api;

import com.eventflowerexchange.dto.request.NotificationRequest;
import com.eventflowerexchange.dto.response.NotificationResponse;
import com.eventflowerexchange.entity.User;
import com.eventflowerexchange.entity.UserNotification;
import com.eventflowerexchange.mapper.NotificationMapper;
import com.eventflowerexchange.service.JwtService;
import com.eventflowerexchange.service.NotificationService;
import com.eventflowerexchange.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class NotificationAPI {
    private final NotificationService notificationService;
    private final JwtService jwtService;
    private final UserService userService;
    private final NotificationMapper notificationMapper;

    @PostMapping("/admin/notification")
    public ResponseEntity<Object> createNotification(
            @RequestBody NotificationRequest notificationRequest
    ) {
        User receiverUser = userService.findUserById(userService.getUserIdByEmail(notificationRequest.getReceiver()));
        if (receiverUser == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        notificationService.createNotification(receiverUser, "Admin", notificationRequest.getType(), notificationRequest.getMessage());
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

    @GetMapping("/admin/notification")
    public ResponseEntity<Object> getAdminNotification() {
        List<UserNotification> userNotifications = notificationService.getAdminNotifications("Admin");
        List<NotificationResponse> notificationResponses = new ArrayList<>();
        userNotifications.forEach(userNotification -> {
            NotificationResponse notificationResponse = notificationMapper.toNotificationResponse(userNotification);
            notificationResponse.setEmailReceiver(userNotification.getReceiverUser().getEmail());
            notificationResponses.add(notificationResponse);
        });
        return new ResponseEntity<>(notificationResponses, HttpStatus.OK);
    }
}
