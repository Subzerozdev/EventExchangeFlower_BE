package com.eventflowerexchange.service.impl;

import com.eventflowerexchange.dto.request.OrderRequestDTO;
import com.eventflowerexchange.entity.Order;
import com.eventflowerexchange.entity.User;
import com.eventflowerexchange.mapper.OrderMapper;
import com.eventflowerexchange.repository.OrderDetailRepository;
import com.eventflowerexchange.repository.OrderRepository;
import com.eventflowerexchange.service.OrderDetailService;
import com.eventflowerexchange.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final OrderDetailService orderDetailService;
    private final OrderMapper orderMapper;

    @Override
    public Order createOrder(OrderRequestDTO orderRequestDTO, User user) {
        // Map request to order entity
        Order order = orderMapper.toOrder(orderRequestDTO);
        // Set other fields
        order.setOrderDate(LocalDateTime.now());
        order.setStatus("Đang chờ duyệt");
        order.setUser(user);
        // Save to DB and return
        return orderRepository.save(order);
    }

    @Override
    public Order updateOrder(Long orderID, OrderRequestDTO orderRequestDTO) {
        Order order = orderRepository.findOrderById(orderID);
        orderMapper.updateOrder(order, orderRequestDTO);
        return orderRepository.save(order);
    }

    @Override
    public void cancelOrder(Long orderID) {
        Order order = orderRepository.findOrderById(orderID);
        orderRepository.delete(order);
    }

    @Override
    public List<Order> getCustomerOrders(String userID) {
        return orderRepository.findOrdersByUserId(userID);
    }

    @Override
    public List<Order> getSellerOrders(String sellerID) {
        return orderRepository.findOrdersBySeller(sellerID);
    }

    @Override
    public void updateOrderStatus(Long orderID, String status) {
        Order order = orderRepository.findOrderById(orderID);
        if (status.equals("0")) {
            order.setStatus("Không được duyệt");
        } else if (status.equals("1")) {
            order.setStatus("Đã duyệt");
        }
    }

}
