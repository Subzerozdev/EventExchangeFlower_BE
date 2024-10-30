package com.eventflowerexchange.api;

import com.eventflowerexchange.dto.request.FeeRequestDTO;
import com.eventflowerexchange.entity.Fee;
import com.eventflowerexchange.entity.Order;
import com.eventflowerexchange.service.FeeService;
import com.eventflowerexchange.service.OrderService;
import com.eventflowerexchange.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
public class AdminAPI {
    private final PostService postService;
    private final OrderService orderService;
    private final FeeService feeService;

    @PutMapping("/posts/{id}/{status}")
    public ResponseEntity<String> updatePostStatus(
            @PathVariable("id") Long id,
            @PathVariable("status") Boolean status
    ) throws Exception {
        postService.updatePostStatus(id, status);
        return new ResponseEntity<>("Successfully Update Status", HttpStatus.OK);
    }

    @GetMapping("/orders")
    public ResponseEntity<Object> getAllOrders() {
        List<Order> orders = orderService.getAllOrders();
        return new ResponseEntity<>(orders, HttpStatus.OK);
    }

    @GetMapping("/fee")
    public ResponseEntity<Object> getFee() {
        Fee fee = feeService.getFeeById(1);
        return new ResponseEntity<>(fee, HttpStatus.OK);
    }

    @PutMapping("/fee")
    public ResponseEntity<String> updateFee(
            @RequestBody FeeRequestDTO feeRequestDTO
    ) {
        feeService.updateFeeAmount(1, feeRequestDTO.getAmount());
        return new ResponseEntity<>("Successfully Update Fee", HttpStatus.OK);
    }

}
