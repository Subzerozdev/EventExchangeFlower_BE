package com.eventflowerexchange.dto.response;

import com.eventflowerexchange.entity.Order;
import com.eventflowerexchange.entity.Shop;
import com.eventflowerexchange.entity.Transactions;
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
    private Transactions transaction;
    private Shop shop;
}
