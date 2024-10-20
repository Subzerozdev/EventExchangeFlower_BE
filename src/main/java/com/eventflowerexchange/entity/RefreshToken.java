//package com.eventflowerexchange.entity;
//
//import jakarta.persistence.*;
//import lombok.AllArgsConstructor;
//import lombok.Builder;
//import lombok.Data;
//import lombok.NoArgsConstructor;
//
//import java.time.LocalDateTime;
//import java.util.Date;
//
//@Entity
//@Data
//@Builder
//@NoArgsConstructor
//@AllArgsConstructor
//public class RefreshToken {
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//
//    @Column(nullable = false)
//    private String refreshToken;
//
//    @Column(nullable = false)
//    private LocalDateTime expirationTime;
//
//    @OneToOne
//    private User user;
//
//}
