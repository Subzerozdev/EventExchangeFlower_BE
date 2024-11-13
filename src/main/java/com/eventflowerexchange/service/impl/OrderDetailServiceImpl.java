package com.eventflowerexchange.service.impl;

import com.eventflowerexchange.dto.request.OrderDetailRequestDTO;
import com.eventflowerexchange.entity.Order;
import com.eventflowerexchange.entity.OrderDetail;
import com.eventflowerexchange.entity.Post;
import com.eventflowerexchange.repository.OrderDetailRepository;
import com.eventflowerexchange.repository.PostRepository;
import com.eventflowerexchange.service.OrderDetailService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderDetailServiceImpl implements OrderDetailService {
    private final OrderDetailRepository orderDetailRepository;
    private final PostRepository postRepository;

    @Override
    public void saveOrderDetails(List<OrderDetailRequestDTO> orderDetails, Order order) {
        orderDetails.forEach(orderDetail -> {
            Post post = postRepository.findPostById(orderDetail.getPostID());
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

    @Override
    public boolean isSameShop(List<OrderDetailRequestDTO> orderDetails){
        boolean result = true;
        String sellerId = null;
        for (OrderDetailRequestDTO orderDetail : orderDetails) {
            Post post = postRepository.findPostById(orderDetail.getPostID());
            if(sellerId==null){
                sellerId = post.getUser().getId();
            } else if (!post.getUser().getId().equals(sellerId)) {
                result = false;
                break;
            }
        }
        return result;
    }
}
