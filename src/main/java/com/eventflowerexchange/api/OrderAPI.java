package com.eventflowerexchange.api;

import com.eventflowerexchange.dto.request.OrderRequestDTO;
import com.eventflowerexchange.entity.Order;
import com.eventflowerexchange.entity.OrderDetail;
import com.eventflowerexchange.entity.User;
import com.eventflowerexchange.service.JwtService;
import com.eventflowerexchange.service.OrderDetailService;
import com.eventflowerexchange.service.OrderService;
import com.eventflowerexchange.service.impl.OrderServiceImpl;
import lombok.RequiredArgsConstructor;
import org.aspectj.weaver.ast.Or;
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

    @PostMapping("/orders")
    public ResponseEntity<Object> placeOrder(
            @RequestBody OrderRequestDTO orderRequestDTO,
            @RequestHeader("Authorization") String jwt
    ) throws Exception {
        User user = jwtService.getUserFromJwtToken(jwt);
        Order order = orderService.createOrder(orderRequestDTO, user);
        orderDetailService.saveOrderDetails(orderRequestDTO.getOrderDetails(), order);
        String vnPayURL = null;
        if (orderRequestDTO.getPaymentMethod().equals("VNPAY")){
            vnPayURL = orderService.createUrl(order, user);
        }
        return ResponseEntity.ok(vnPayURL);
    }

    @PostMapping("/orders/{id}")
    public ResponseEntity<Object> updateOrder(
            @RequestBody OrderRequestDTO orderRequestDTO,
            @PathVariable("id") Long id
    ){
        Order order = orderService.updateOrder(id, orderRequestDTO);
        return new ResponseEntity<>(order, HttpStatus.OK);
    }

    @GetMapping("/orders")
    public ResponseEntity<Object> getUserOrders(
            @RequestHeader("Authorization") String jwt
    ){
        User user = jwtService.getUserFromJwtToken(jwt);
        List<Order> orders = orderService.getCustomerOrders(user.getId());
        return new ResponseEntity<>(orders, HttpStatus.OK);
    }

    @DeleteMapping("/orders/{id}")
    public ResponseEntity<Object> deleteOrder(
            @PathVariable("id") Long id
    ){
        orderService.cancelOrder(id);
        return new ResponseEntity<>("Delete Success", HttpStatus.OK);
    }

    @GetMapping("/orders/{id}")
    public ResponseEntity<Object> getOrderDetails(@PathVariable("id") Long id){
        List<OrderDetail> orderDetails = orderDetailService.getOrderDetailsByOrderId(id);
        return new ResponseEntity<>(orderDetails,HttpStatus.OK);
    }

    @GetMapping("/seller/orders")
    public ResponseEntity<Object> getSellerOrders(
            @RequestHeader("Authorization") String jwt
    ){
        String userID = jwtService.getUserIdFromJwtToken(jwt);
        List<Order> orders = orderService.getSellerOrders(userID);
        return new ResponseEntity<>(orders,HttpStatus.OK);
    }

    @PutMapping("/seller/orders/{id}/{status}")
    public ResponseEntity<Object> updateOrder(
            @PathVariable("id") Long id,
            @PathVariable("status") String status
    ){
        orderService.updateOrderStatus(id, status);
        return new ResponseEntity<>("Update Success", HttpStatus.OK);
    }

    @PostMapping("/transactions")
    public ResponseEntity<Object> createTransaction(
            @RequestParam String orderID
    ) throws Exception{
      orderService.createTransactions(Long.parseLong(orderID));
        return new ResponseEntity<>(" Success", HttpStatus.OK);
    }
}
