package com.eventflowerexchange.dto.response;

import com.eventflowerexchange.entity.Order;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderResponseDTO {
    private Order order;
    private float totalFee;
}
