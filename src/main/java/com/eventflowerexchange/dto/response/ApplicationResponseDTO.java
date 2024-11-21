package com.eventflowerexchange.dto.response;

import com.eventflowerexchange.entity.APPLICATION_STATUS;
import com.eventflowerexchange.entity.APPLICATION_TYPE;
import com.eventflowerexchange.entity.Order;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ApplicationResponseDTO {
    private int id;
    private String problem;
    private String content;
    private APPLICATION_STATUS status;
    private APPLICATION_TYPE type;
    private Order order;
    private String userEmail;
}
