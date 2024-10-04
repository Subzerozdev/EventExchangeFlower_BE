package com.eventflowerexchange.api;

import com.eventflowerexchange.dto.request.AuthRequestDTO;
import com.eventflowerexchange.dto.request.UserRequestDTO;
import com.eventflowerexchange.dto.response.AuthResponseDTO;
import com.eventflowerexchange.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthAPI {
    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity<String> register(@Valid @RequestBody UserRequestDTO userRequestDTO) {
        String response = userService.register(userRequestDTO);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponseDTO> login(@Valid @RequestBody AuthRequestDTO authRequestDTO) {
        AuthResponseDTO response = userService.login(authRequestDTO);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}