//package com.eventflowerexchange.entity;
//
//import com.fasterxml.jackson.annotation.JsonIgnore;
//import jakarta.persistence.*;
//import lombok.AllArgsConstructor;
//import lombok.Builder;
//import lombok.Data;
//import lombok.NoArgsConstructor;
//
//import java.time.LocalDateTime;
//
//@Entity
//@Data
//@Builder
//@NoArgsConstructor
//@AllArgsConstructor
//public class RefreshToken {
//    @JsonIgnore
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
//    @JsonIgnore
//    @Column(nullable = false)
//    private boolean isRevoked;
//
//    @JsonIgnore
//    @OneToOne
//    private User user;
//
//}
