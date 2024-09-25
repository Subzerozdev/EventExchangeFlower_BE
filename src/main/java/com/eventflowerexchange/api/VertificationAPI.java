package com.eventflowerexchange.api;

import com.eventflowerexchange.dto.MailBody;
import com.eventflowerexchange.entity.OTPEmail;
import com.eventflowerexchange.entity.User;
import com.eventflowerexchange.repository.OTPEmailRepository;
import com.eventflowerexchange.service.EmailService;
import com.eventflowerexchange.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/vertification")
public class VertificationAPI {
@Autowired
private UserService userService;
@Autowired
private EmailService emailService;
@Autowired
private OTPEmailRepository otpEmailRepository;

    @PostMapping ("/{email}")
        public ResponseEntity <String> VerifyEmail (@PathVariable String email) {

        User user =userService.getUserByEmail(email);
        Long OTP  = emailService.generateOTP();
        MailBody mailBody =MailBody.builder()
                .to(email)
                .subject("Verify the user's email")
                .body("E-"+OTP + " là mã xác minh Email của bạn.")
                .build();
        emailService.sendEmail(mailBody);


        OTPEmail otpEmail = OTPEmail.builder()
                .OTP(OTP)
                .expiryDate(LocalDateTime.now().plusSeconds(90))
                .user(user)
                .build();

       otpEmailRepository.save(otpEmail);

        return  ResponseEntity.ok("Please check your email!!!");
        }

        @PostMapping("/{otp}/{email}")
    public ResponseEntity <String> VerifyOTP (@PathVariable Long otp, @PathVariable String email) {
        User user = userService.getUserByEmail(email);
        OTPEmail otpEmail = otpEmailRepository.findByOtpAndUser(otp, user).get();

        if ( otpEmail.getExpiryDate().isBefore(LocalDateTime.now()) ) {
           otpEmailRepository.delete(otpEmail);
           return  new ResponseEntity<>("OTP has expired", HttpStatus.EXPECTATION_FAILED);
        }
        return ResponseEntity.ok("OTP Verify!!");
        }
}
