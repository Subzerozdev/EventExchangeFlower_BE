//package com.eventflowerexchange.service.impl;
//
//import com.eventflowerexchange.entity.RefreshToken;
//import com.eventflowerexchange.entity.User;
//import com.eventflowerexchange.repository.RefreshTokenRepository;
//import com.eventflowerexchange.repository.UserRepository;
//import com.eventflowerexchange.service.RefreshTokenService;
//import lombok.RequiredArgsConstructor;
//import org.springframework.stereotype.Service;
//
//import java.time.LocalDateTime;
//import java.util.UUID;
//
//@Service
//@RequiredArgsConstructor
//public class RefreshTokenServiceImpl implements RefreshTokenService {
//    private final RefreshTokenRepository refreshTokenRepository;
//    private final UserRepository userRepository;
//
//    @Override
//    public RefreshToken createRefreshToken(String email) {
//        // Get user by email
//        User user = userRepository.findUserByEmail(email);
//        // Get refresh token from user
//        RefreshToken refreshToken = user.getRefreshToken();
//        // Create token if null
//        if (refreshToken == null) {
//            refreshToken = RefreshToken.builder()
//                    .refreshToken(UUID.randomUUID().toString())
//                    .expirationTime(LocalDateTime.now().plusSeconds(60 * 60 * 24 * 2))
//                    .isRevoked(false)
//                    .user(user)
//                    .build();
//            refreshTokenRepository.save(refreshToken);
//        }
//        return refreshToken;
//    }
//
//    @Override
//    public RefreshToken verifyRefreshToken(String refreshToken) {
//        RefreshToken refreshTokenEntity = refreshTokenRepository.findByRefreshToken(refreshToken);
//        if (refreshTokenEntity.getExpirationTime().isBefore(LocalDateTime.now())) {
//            refreshTokenRepository.delete(refreshTokenEntity);
//        }
//        return refreshTokenEntity;
//    }
//}
