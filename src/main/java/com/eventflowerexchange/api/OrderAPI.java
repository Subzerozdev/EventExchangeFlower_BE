package com.eventflowerexchange.api;

import com.eventflowerexchange.dto.SellerInformation;
import com.eventflowerexchange.dto.request.OrderRequestDTO;
import com.eventflowerexchange.dto.request.UpdateValidationImageRequest;
import com.eventflowerexchange.dto.response.OrderDetailResponseDTO;
import com.eventflowerexchange.entity.*;
import com.eventflowerexchange.service.*;
import com.eventflowerexchange.util.FieldValidation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class OrderAPI {
    private final OrderService orderService;
    private final JwtService jwtService;
    private final OrderDetailService orderDetailService;
    private final TransactionService transactionService;
    private final FeeService feeService;
    private final PostService postService;
    private final NotificationService notificationService;

    @PostMapping("/orders")
    public ResponseEntity<Object> placeOrder(
            @RequestBody OrderRequestDTO orderRequestDTO,
            @RequestHeader("Authorization") String jwt
    ) throws Exception {
        User user = jwtService.getUserFromJwtToken(jwt);
        List<Post> posts = new ArrayList<>();
        orderRequestDTO.getOrderDetails().forEach(orderDetailRequestDTO ->
                posts.add(postService.getPostById(orderDetailRequestDTO.getPostID())));

        List<Order> orders = new ArrayList<>();
        Map<String, List<Post>> postListInOrder = posts.stream().collect(
                Collectors.groupingBy(p -> p.getUser().getId()));
        for (var entry : postListInOrder.entrySet()) {
            if (entry.getValue().get(0).getUser().getId().equals(user.getId())) {
                StringBuilder message = new StringBuilder();
                for (Post post : entry.getValue()) {
                    message.append(post.getName()).append("; ");
                }
                return new ResponseEntity<>(message.substring(0, message.length() - 2), HttpStatus.BAD_REQUEST);
            }
            Order order = orderService.createOrder(orderRequestDTO, user, entry.getValue());
            orderDetailService.saveOrderDetails(entry.getValue(), order);
            orders.add(order);
        }
        String vnPayURL = orderService.createUrl(orders, orderRequestDTO.getTotalMoney(), user);
        return ResponseEntity.ok(vnPayURL);
    }

    @GetMapping("/orders")
    public ResponseEntity<Object> getUserOrders(
            @RequestHeader("Authorization") String jwt
    ) {
        User user = jwtService.getUserFromJwtToken(jwt);
        List<Order> orders = orderService.getCustomerOrders(user.getId());
        return new ResponseEntity<>(orders, HttpStatus.OK);
    }

    @GetMapping("/orders/{id}")
    public ResponseEntity<Object> getOrderDetails(@PathVariable("id") Long id) {
        Order order = orderService.getOrderById(id);
        List<OrderDetail> orderDetails = orderDetailService.getOrderDetailsByOrderId(id);
        User seller = orderDetails.get(0).getPost().getUser();
        SellerInformation sellerInformation = SellerInformation.builder()
                .email(seller.getEmail())
                .phone(seller.getPhone())
                .build();
        OrderDetailResponseDTO orderDetailResponseDTO = OrderDetailResponseDTO.builder()
                .order(order)
                .orderDetail(orderDetails)
                .sellerInformation(sellerInformation)
                .totalFee(feeService.getFeeAmountById(order.getFeeId()) * order.getTotalMoney())
                .build();
        return new ResponseEntity<>(orderDetailResponseDTO, HttpStatus.OK);
    }

    @GetMapping("/seller/orders")
    public ResponseEntity<Object> getSellerOrders(
            @RequestHeader("Authorization") String jwt
    ) {
        String userID = jwtService.getUserIdFromJwtToken(jwt);
        List<Order> orders = orderService.getSellerOrders(userID);
        return new ResponseEntity<>(orders, HttpStatus.OK);
    }

    @PutMapping("/seller/orders/{id}")
    public ResponseEntity<Object> updateOrderStatus(
            @PathVariable("id") Long id,
            @RequestBody UpdateValidationImageRequest validationImage
    ) {
        String image = validationImage.getImage();
        if (image != null && !image.isEmpty()) {
            orderService.updateOrderStatusIsPicked(id, validationImage.getImage());
            notificationService.createNotification(orderService.getOrderById(id).getUser(), "System", NOTIFICATION_TYPE.INFORMATION, "Đơn hàng " + id + " của bạn đã giao thành công");
            return new ResponseEntity<>("Update Success", HttpStatus.OK);
        }
        return new ResponseEntity<>("Image is invalid!", HttpStatus.BAD_REQUEST);
    }

    @DeleteMapping("/seller/orders")
    public ResponseEntity<Object> cancelOrder(
    ) {
        return new ResponseEntity<>("Image is invalid!", HttpStatus.BAD_REQUEST);
    }

    @PostMapping("/transactions")
    public ResponseEntity<Object> createTransaction(
            @RequestParam(name = "orderID") List<String> orderIDList
    ) {
        for (String orderID : orderIDList) {
            Order order = orderService.getOrderById(Long.valueOf(orderID));
            FieldValidation.checkObjectExist(order, "Order");
            transactionService.createTransaction01(order);
        }
        return new ResponseEntity<>("Success", HttpStatus.OK);
    }
}
