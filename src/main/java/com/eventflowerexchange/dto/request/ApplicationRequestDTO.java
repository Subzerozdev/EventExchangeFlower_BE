package com.eventflowerexchange.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ApplicationRequestDTO {
    private String problem;
    private String content;
    private Long orderId;
    private String ownerBank;
    private String bankNumber;
    private String bankName;
}
