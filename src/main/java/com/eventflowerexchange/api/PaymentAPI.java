package com.eventflowerexchange.api;

import com.eventflowerexchange.entity.*;
import com.eventflowerexchange.repository.OrderRepository;
import com.eventflowerexchange.repository.PostRepository;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

@RestController
@RequestMapping("/payment")
@RequiredArgsConstructor
public class PaymentAPI {
    private final OrderRepository orderRepository;
    private final PostRepository postRepository;

    @GetMapping("/vnpay/callback")
    public ResponseEntity<Object> payCallbackHandler(HttpServletRequest request) {
        String status = request.getParameter("vnp_ResponseCode");
        String orderID = request.getParameter("orderID");
        if (status.equals("00")) {
            Order order = orderRepository.findOrderById(Long.parseLong(orderID));
            order.setStatus(ORDER_STATUS.AWAITING_PICKUP);
            orderRepository.save(order);
            List<OrderDetail> orderDetails = order.getOrderDetails();
            for (OrderDetail orderDetail : orderDetails) {
                Post post = orderDetail.getPost();
                post.setStatus(POST_STATUS.SOLD_OUT);
                postRepository.save(post);
            }
            return new ResponseEntity<>("Order Successfully", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Order failed", HttpStatus.BAD_REQUEST);
        }
    }
}
