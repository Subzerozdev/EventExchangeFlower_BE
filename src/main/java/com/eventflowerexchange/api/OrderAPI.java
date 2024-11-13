package com.eventflowerexchange.api;

import com.eventflowerexchange.dto.SellerInformation;
import com.eventflowerexchange.dto.request.OrderRequestDTO;
import com.eventflowerexchange.dto.response.OrderDetailResponseDTO;
import com.eventflowerexchange.entity.ORDER_STATUS;
import com.eventflowerexchange.entity.Order;
import com.eventflowerexchange.entity.OrderDetail;
import com.eventflowerexchange.entity.User;
import com.eventflowerexchange.repository.OrderRepository;
import com.eventflowerexchange.service.*;
import com.eventflowerexchange.util.FieldValidation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class OrderAPI {
    private final OrderService orderService;
    private final JwtService jwtService;
    private final OrderDetailService orderDetailService;
    private final TransactionService transactionService;
    private final FeeService feeService;
    private final OrderRepository orderRepository;

    @PostMapping("/orders")
    public ResponseEntity<Object> placeOrder(
            @RequestBody OrderRequestDTO orderRequestDTO,
            @RequestHeader("Authorization") String jwt
    ) throws Exception {
        User user = jwtService.getUserFromJwtToken(jwt);
        Order order = orderService.createOrder(orderRequestDTO, user);
        if (order == null) {
            return new ResponseEntity<>("Cannot place order with different shop", HttpStatus.BAD_REQUEST);
        }
        orderDetailService.saveOrderDetails(orderRequestDTO.getOrderDetails(), order);
        String vnPayURL = orderService.createUrl(order, user);
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
                .totalFee(feeService.getFeeAmountById(order.getFeeId())*order.getTotalMoney())
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

    @PutMapping("/seller/orders/{id}/{status}")
    public ResponseEntity<Object> updateOrderStatus(
            @PathVariable("id") Long id,
            @PathVariable("status") Boolean status
    ) {
        orderService.updateOrderStatus(id, status);
        return new ResponseEntity<>("Update Success", HttpStatus.OK);
    }

    @PostMapping("/transactions")
    public ResponseEntity<Object> createTransaction(
            @RequestParam Long orderID
    ){
        Order order = orderService.getOrderById(orderID);
        FieldValidation.checkObjectExist(order, "Order");
        transactionService.createTransaction01(order);
        return new ResponseEntity<>("Success", HttpStatus.OK);
    }

    @PutMapping("/receive/{orderId}")
    public ResponseEntity<Object> receiveOrder(
            @PathVariable Long orderId
    ){
        Order order = orderService.getOrderById(orderId);
        FieldValidation.checkObjectExist(order, "Order");
        if (order.getStatus().equals(ORDER_STATUS.AWAITING_PICKUP)) {
            order.setStatus(ORDER_STATUS.PICKED_UP);
            orderRepository.save(order);
        }
        return new ResponseEntity<>("Success", HttpStatus.OK);
    }
}
