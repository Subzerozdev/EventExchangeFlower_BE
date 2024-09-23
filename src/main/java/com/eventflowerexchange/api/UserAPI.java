package com.eventflowerexchange.api;

import com.eventflowerexchange.dto.request.UserRequestDTO;
import com.eventflowerexchange.entity.User;
import com.eventflowerexchange.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
public class UserAPI {

    //DI: Dependency Injection
    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity<Object> register(@Valid @RequestBody User user) {
        User newUser = userService.register(user);
        return ResponseEntity.ok(newUser);
    }
    
    @PostMapping("/login")
    public ResponseEntity<Object> login(@Valid @RequestBody User user) throws Exception {
        User userLogin = userService.login(user);
        return ResponseEntity.ok(userLogin);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> updateUserProfile(
            @Valid @RequestBody UserRequestDTO userRequest,
            @PathVariable("id") Long id) {
        User user = userService.updateUserById(id, userRequest);
        return ResponseEntity.ok(user);
    }
}
