package com.eventflowerexchange.api;

import com.eventflowerexchange.dto.response.UserResponseDTO;
import com.eventflowerexchange.entity.User;
import com.eventflowerexchange.mapper.UserMapper;
import com.eventflowerexchange.service.DashBoardService;
import com.eventflowerexchange.service.JwtService;
import com.eventflowerexchange.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class DashBoardAPI {
    private final DashBoardService dashBoardService;
    private final UserService userService;
    private final JwtService jwtService;
    private final UserMapper userMapper;

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
        List<UserResponseDTO> usersResponse = new ArrayList<>();
        users.forEach(user -> {
            UserResponseDTO userResponseDTO = userMapper.toUserResponseDTO(user);
            usersResponse.add(userResponseDTO);
        });
        return ResponseEntity.ok(usersResponse);
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
