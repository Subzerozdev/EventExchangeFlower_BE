package com.eventflowerexchange.service;

import com.eventflowerexchange.dto.request.OrderDetailRequestDTO;
import com.eventflowerexchange.entity.Order;
import com.eventflowerexchange.entity.OrderDetail;

import java.util.List;

public interface OrderDetailService {
    void saveOrderDetails(List<OrderDetailRequestDTO> orderDetails, Order order);
    List<OrderDetail> getOrderDetailsByOrderId(Long orderId);
}
