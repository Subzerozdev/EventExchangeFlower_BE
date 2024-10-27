package com.eventflowerexchange.api;

import com.eventflowerexchange.entity.User;
import com.eventflowerexchange.service.DashBoardService;
import com.eventflowerexchange.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
public class DashBoardAPI {
    private final DashBoardService dashBoardService;
    private final UserService userService;

    @GetMapping("/status")
    public ResponseEntity<Object> getStatus() {
        Map<String,Object> status = dashBoardService.getDashBoard();
        return ResponseEntity.ok(status);
    }

    @GetMapping("/status/{shop_id}")
    public ResponseEntity<Object> getMonthlyRevenue(@PathVariable String shop_id) {
        Map<String,Object> revenueMonthly = dashBoardService.getMonthlyRevenue(shop_id);
        return ResponseEntity.ok(revenueMonthly);
    }

    @DeleteMapping("/user/{id}")
    public ResponseEntity<Object> deleteUser(@PathVariable String id) {
        userService.deleteUserById(id);
        return ResponseEntity.ok("Deleted successfully");
    }

    @GetMapping("/user")
    public ResponseEntity<Object> getUser() {
        List<User> users = userService.getUsers();
        return ResponseEntity.ok(users);
    }
}
