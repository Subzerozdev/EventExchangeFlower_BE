package com.eventflowerexchange.entity;

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

public class OTPEmail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    Long OTP;
    LocalDateTime expiryDate;
    @OneToOne
    User user;

}
