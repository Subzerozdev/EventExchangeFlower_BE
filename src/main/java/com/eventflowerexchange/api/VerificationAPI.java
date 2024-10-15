package com.eventflowerexchange.api;

import com.eventflowerexchange.dto.MailBody;
import com.eventflowerexchange.entity.OTPEmail;
import com.eventflowerexchange.entity.User;
import com.eventflowerexchange.repository.OTPEmailRepository;
import com.eventflowerexchange.repository.UserRepository;
import com.eventflowerexchange.service.EmailService;
import com.eventflowerexchange.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/verification")
@RequiredArgsConstructor
public class VerificationAPI {
    private final UserService userService;
    private final EmailService emailService;
    private final OTPEmailRepository otpEmailRepository;
    private final UserRepository userRepository;

    @PostMapping("/{id}")
    public ResponseEntity<String> VerifyEmail(@PathVariable String id) {
        User user = userService.findUserById(id);
        Long OTP = emailService.generateOTP();
        MailBody mailBody = emailService.createEmail(user.getEmail(), "Verify the user's email", "Mã xác minh Email của bạn: " + OTP);
        emailService.sendEmail(mailBody);
        emailService.saveOTP(user, OTP);
        return ResponseEntity.ok("Please check your email!!!");
    }

    @PostMapping("/{otp}/{id}")
    public ResponseEntity<String> VerifyOTP(@PathVariable Long otp, @PathVariable String id) {
        User user = userService.findUserById(id);
        OTPEmail otpEmail = emailService.findOtpEmail(user, otp);
        if (emailService.checkOtp(otpEmail)) {
            otpEmailRepository.delete(otpEmail);
            return new ResponseEntity<>("OTP has expired", HttpStatus.EXPECTATION_FAILED);
        }
        user.setActive(true);
        userRepository.save(user);
        return ResponseEntity.ok("OTP Verify!!");
    }
}
