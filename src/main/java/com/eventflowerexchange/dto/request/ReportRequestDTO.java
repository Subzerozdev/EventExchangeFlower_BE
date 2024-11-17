package com.eventflowerexchange.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReportRequestDTO {
    private String problem;
    private String content;
    private Long orderId;
}
