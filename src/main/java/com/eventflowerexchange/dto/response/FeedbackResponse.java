package com.eventflowerexchange.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class FeedbackResponse {
    Long id;
    String content;
    int rating;
    String email;

}
