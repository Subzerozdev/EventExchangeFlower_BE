package com.eventflowerexchange.service;

import com.eventflowerexchange.entity.Order;
import com.eventflowerexchange.entity.OrderDetail;
import com.eventflowerexchange.entity.Post;

import java.util.List;

public interface OrderDetailService {
    void saveOrderDetails(List<Post> posts, Order order);
    List<OrderDetail> getOrderDetailsByOrderId(Long orderId);
}
