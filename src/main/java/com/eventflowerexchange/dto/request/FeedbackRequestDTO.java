package com.eventflowerexchange.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FeedbackRequestDTO {
    private String content;
    private int rating;
    // khỏi lấy user, nó đăng nhập vào nó tự lấy token
    private String shopID;
}
