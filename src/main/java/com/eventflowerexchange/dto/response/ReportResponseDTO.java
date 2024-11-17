package com.eventflowerexchange.dto.response;

import com.eventflowerexchange.entity.REPORT_STATUS;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ReportResponseDTO {
    private int id;
    private String problem;
    private String content;
    private REPORT_STATUS status;
    private Long orderId;
    private String userEmail;
}
