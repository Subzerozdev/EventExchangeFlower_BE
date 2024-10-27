package com.eventflowerexchange.api;

import com.eventflowerexchange.entity.User;
import com.eventflowerexchange.service.DashBoardService;
import com.eventflowerexchange.service.JwtService;
import com.eventflowerexchange.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class DashBoardAPI {
    private final DashBoardService dashBoardService;
    private final UserService userService;
    private final JwtService jwtService;

    @GetMapping("/admin/status")
    public ResponseEntity<Object> getStatus() {
        Map<String,Object> status = dashBoardService.getDashBoard();
        return ResponseEntity.ok(status);
    }

    @DeleteMapping("/admin/user/{id}")
    public ResponseEntity<Object> deleteUser(@PathVariable String id) {
        userService.deleteUserById(id);
        return ResponseEntity.ok("Deleted successfully");
    }

    @GetMapping("/admin/user")
    public ResponseEntity<Object> getUser() {
        List<User> users = userService.getUsers();
        return ResponseEntity.ok(users);
    }

    @GetMapping("/status")
    public ResponseEntity<Object> getMonthlyRevenue(
            @RequestHeader("Authorization") String jwt
    ) {
        String userId = jwtService.getUserIdFromJwtToken(jwt);
        Map<String,Object> revenueMonthly = dashBoardService.getMonthlyRevenue(userId);
        return ResponseEntity.ok(revenueMonthly);
    }
}
