package com.eventflowerexchange.api;

import com.eventflowerexchange.dto.request.OrderRequestDTO;
import com.eventflowerexchange.entity.Order;
import com.eventflowerexchange.entity.OrderDetail;
import com.eventflowerexchange.entity.User;
import com.eventflowerexchange.service.JwtService;
import com.eventflowerexchange.service.OrderDetailService;
import com.eventflowerexchange.service.OrderService;
import com.eventflowerexchange.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderAPI {
    private final OrderService orderService;
    private final JwtService jwtService;
    private final OrderDetailService orderDetailService;

    @PostMapping("")
    public ResponseEntity<Object> placeOrder(
            @RequestBody OrderRequestDTO orderRequestDTO,
            @RequestHeader("Authorization") String jwt
    ){
        User user = jwtService.getUserFromJwtToken(jwt);
        Order order = orderService.createOrder(orderRequestDTO, user);
        orderDetailService.saveOrderDetails(orderRequestDTO.getOrderDetails(), order);
        return new ResponseEntity<>(order, HttpStatus.CREATED);
    }

    @PostMapping("/{id}")
    public ResponseEntity<Object> updateOrder(
            @RequestBody OrderRequestDTO orderRequestDTO,
            @PathVariable("id") Long id
    ){
        Order order = orderService.updateOrder(id, orderRequestDTO);
        return new ResponseEntity<>(order, HttpStatus.OK);
    }

    @GetMapping("")
    public ResponseEntity<Object> getUserOrders(
            @RequestHeader("Authorization") String jwt
    ){
        User user = jwtService.getUserFromJwtToken(jwt);
        List<Order> orders = orderService.getCustomerOrders(user.getId());
        return new ResponseEntity<>(orders, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteOrder(
            @PathVariable("id") Long id
    ){
        orderService.cancelOrder(id);
        return new ResponseEntity<>("Delete Success", HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getOrderDetails(@PathVariable("id") Long id){
        List<OrderDetail> orderDetails = orderDetailService.getOrderDetailsByOrderId(id);
        return new ResponseEntity<>(orderDetails,HttpStatus.OK);
    }
}
