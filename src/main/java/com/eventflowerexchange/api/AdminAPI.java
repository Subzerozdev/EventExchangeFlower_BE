package com.eventflowerexchange.api;

import com.eventflowerexchange.entity.Order;
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
}
