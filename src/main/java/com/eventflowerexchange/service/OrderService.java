package com.eventflowerexchange.service;

import com.eventflowerexchange.dto.request.OrderRequestDTO;
import com.eventflowerexchange.entity.Order;
import com.eventflowerexchange.entity.Post;
import com.eventflowerexchange.entity.User;

import java.util.List;

public interface OrderService {
    Order createOrder(OrderRequestDTO orderRequestDTO, User user, List<Post> postsInOrder);
    Order getOrderById(Long orderID);
    List<Order> getAllOrders();
    List<Order> getCustomerOrders(String userID);
    List<Order> getSellerOrders(String sellerID);
    Order updateOrderStatusIsPicked(Long orderID, String image);
    Order cancelOrder(Long orderID);
    String createUrl(List<Order> order, float totalMoney, User user) throws Exception;
}
