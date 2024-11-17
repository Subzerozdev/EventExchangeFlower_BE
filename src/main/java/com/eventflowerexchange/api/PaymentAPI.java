package com.eventflowerexchange.api;

import com.eventflowerexchange.entity.*;
import com.eventflowerexchange.repository.OrderRepository;
import com.eventflowerexchange.repository.PostRepository;
import com.eventflowerexchange.service.NotificationService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

@RestController
@RequestMapping("/payment")
@RequiredArgsConstructor
public class PaymentAPI {
    private final OrderRepository orderRepository;
    private final PostRepository postRepository;
    private final NotificationService notificationService;

    @GetMapping("/vnpay/callback")
    public ResponseEntity<Object> payCallbackHandler(HttpServletRequest request) {
        String status = request.getParameter("vnp_ResponseCode");
        String[] orderIDList = request.getParameterValues("orderID");
        if (status.equals("00")) {
            for (String orderID : orderIDList) {
                Order order = orderRepository.findOrderById(Long.parseLong(orderID));
                order.setStatus(ORDER_STATUS.AWAITING_PICKUP);
                orderRepository.save(order);
                List<OrderDetail> orderDetails = order.getOrderDetails();
                for (OrderDetail orderDetail : orderDetails) {
                    Post post = orderDetail.getPost();
                    post.setStatus(POST_STATUS.SOLD_OUT);
                    postRepository.save(post);
                }
                notificationService.createNotification(order.getUser(), "System", NOTIFICATION_TYPE.INFORMATION, "Bạn đã thanh toán thành công cho đơn hàng " + orderID + ". Đơn hàng sẽ được giao sau ngày kết thúc sự kiện của bài đăng");
            }
            return new ResponseEntity<>("Order Successfully", HttpStatus.OK);
        } else {
            for (String orderID : orderIDList) {
                Order order = orderRepository.findOrderById(Long.parseLong(orderID));
                order.setStatus(ORDER_STATUS.CANCELLED);
                orderRepository.save(order);
                notificationService.createNotification(order.getUser(), "System", NOTIFICATION_TYPE.INFORMATION, "Đơn hàng số " + orderID + " đã bị hủy do thanh toán không thành công");
            }
            return new ResponseEntity<>("Order failed", HttpStatus.BAD_REQUEST);
        }
    }
}
