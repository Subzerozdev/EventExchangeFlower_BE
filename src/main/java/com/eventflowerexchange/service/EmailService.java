package com.eventflowerexchange.service;

import com.eventflowerexchange.dto.MailBody;

public interface EmailService {


    Long generateOTP();
    void sendEmail(MailBody mailBody);
}
