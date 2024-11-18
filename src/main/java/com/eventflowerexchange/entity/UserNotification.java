package com.eventflowerexchange.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "user_notification")
public class UserNotification {
    @JsonIgnore
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String message;
    private String sender;
    private LocalDateTime createDate;
    @Column(name = "notification_id")
    private NOTIFICATION_TYPE notificationType;

    @JsonIgnore
    @JoinColumn(name = "receiver_user")
    @ManyToOne
    private User receiverUser;

}
