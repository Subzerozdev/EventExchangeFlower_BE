package com.eventflowerexchange.dto.response;

import com.eventflowerexchange.dto.SellerInformation;
import com.eventflowerexchange.entity.Order;
import com.eventflowerexchange.entity.OrderDetail;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderDetailResponseDTO {
    private Order order;
    private List<OrderDetail> orderDetail;
    private SellerInformation sellerInformation;
    private float totalFee;
}
