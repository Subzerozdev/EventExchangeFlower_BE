package com.eventflowerexchange.service;

import com.eventflowerexchange.dto.request.OrderDetailRequestDTO;
import com.eventflowerexchange.entity.Order;
import com.eventflowerexchange.entity.OrderDetail;

import java.util.List;

public interface OrderDetailService {
    List<OrderDetail> saveOrderDetails(
            List<OrderDetailRequestDTO> orderDetailRequestDTOs,
            Order order
    );

    List<OrderDetail> getOrderDetailsByOrderId(Long orderId);
}
