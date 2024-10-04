package com.eventflowerexchange.service;

import com.eventflowerexchange.dto.request.OrderRequestDTO;
import com.eventflowerexchange.entity.Order;
import com.eventflowerexchange.entity.User;

import java.util.List;

public interface OrderService {
    Order createOrder(OrderRequestDTO orderRequestDTO, User user);
    Order updateOrder(Long orderID, OrderRequestDTO orderRequestDTO);
    void cancelOrder(Long orderID);
    List<Order> getCustomerOrders(String userID);
    List<Order> getSellerOrders(String userID);
}