package com.eventflowerexchange.api;

import com.eventflowerexchange.entity.User;
import com.eventflowerexchange.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserAPI {

    //DI: Dependency Injection
    @Autowired
    UserService userService;

    @PostMapping("/register")
    public ResponseEntity register (@Valid @RequestBody User user) {
        User newUser  =   userService.register(user);
        return ResponseEntity.ok(newUser);
    }

}
