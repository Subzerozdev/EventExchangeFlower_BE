package com.eventflowerexchange.service.impl;

import com.eventflowerexchange.entity.Order;
import com.eventflowerexchange.entity.OrderDetail;
import com.eventflowerexchange.entity.Post;
import com.eventflowerexchange.repository.OrderDetailRepository;
import com.eventflowerexchange.service.OrderDetailService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderDetailServiceImpl implements OrderDetailService {
    private final OrderDetailRepository orderDetailRepository;

    @Override
    public void saveOrderDetails(List<Post> posts, Order order) {
        posts.forEach(post -> {
            OrderDetail orderDetailSaved = OrderDetail.builder()
                    .totalMoney(post.getPrice())
                    .post(post)
                    .order(order)
                    .build();
            orderDetailRepository.save(orderDetailSaved);
        });
    }

    @Override
    public List<OrderDetail> getOrderDetailsByOrderId(Long orderId) {
        return orderDetailRepository.findByOrderId(orderId);
    }
}
