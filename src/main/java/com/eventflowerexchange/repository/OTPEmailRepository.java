package com.eventflowerexchange.repository;

import com.eventflowerexchange.entity.OTPEmail;
import com.eventflowerexchange.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface OTPEmailRepository extends JpaRepository<OTPEmail, Long> {

    @Query("SELECT otp " +
            "FROM OTPEmail otp " +
            "WHERE otp.OTP=?1 " +
            "AND otp.user=?2")
    Optional<OTPEmail> findByOtpAndUser(Long otp, User user);

}
