package com.eventflowerexchange.service;

import com.eventflowerexchange.dto.MailBody;
import com.eventflowerexchange.entity.OTPEmail;
import com.eventflowerexchange.entity.User;

public interface EmailService {
    Long generateOTP();
    MailBody createEmail(String email, String subject, String body);
    void sendEmail(MailBody mailBody);
    void saveOTP(User user, Long OTP);
    OTPEmail findOtpEmail(User user, Long OTP);
    boolean checkOtp(OTPEmail otpEmail);
}
