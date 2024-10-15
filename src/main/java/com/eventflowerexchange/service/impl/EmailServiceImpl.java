package com.eventflowerexchange.service.impl;

import com.eventflowerexchange.dto.MailBody;
import com.eventflowerexchange.entity.OTPEmail;
import com.eventflowerexchange.entity.User;
import com.eventflowerexchange.repository.OTPEmailRepository;
import com.eventflowerexchange.service.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService {
    private final JavaMailSender javaMailSender;
    private final OTPEmailRepository otpEmailRepository;

    @Override
    public Long generateOTP() {
        Random random = new Random();
        return random.nextLong(100_100, 999_999);
    }

    @Override
    public MailBody createEmail(String email, String subject, String body) {
        return MailBody.builder()
                .to(email)
                .subject(subject)
                .body(body)
                .build();
    }

    @Override
    public void sendEmail(MailBody mailBody) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(mailBody.getTo());
        message.setSubject(mailBody.getSubject());
        message.setText(mailBody.getBody());
        javaMailSender.send(message);
    }

    @Override
    public void saveOTP(User user, Long OTP) {
        OTPEmail otpEmail = otpEmailRepository.findByUserId(user.getId());
        if (otpEmail != null) {
            otpEmail.setOTP(OTP);
            otpEmail.setExpiryDate(LocalDateTime.now().plusSeconds(90));
        } else {
            otpEmail = OTPEmail.builder()
                    .OTP(OTP)
                    .expiryDate(LocalDateTime.now().plusSeconds(90))
                    .user(user)
                    .build();
        }
        otpEmailRepository.save(otpEmail);
    }

    @Override
    public OTPEmail findOtpEmail(User user, Long OTP) {
        return otpEmailRepository.findByOtpAndUser(OTP, user);
    }

    @Override
    public boolean checkOtp(OTPEmail otpEmail) {
        return otpEmail.getExpiryDate().isBefore(LocalDateTime.now());
    }

}
