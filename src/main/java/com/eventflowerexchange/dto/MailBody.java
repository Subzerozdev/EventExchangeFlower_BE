package com.eventflowerexchange.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MailBody {

    String to;
    String subject;
    String body;

}
