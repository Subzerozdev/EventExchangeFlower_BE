package com.eventflowerexchange.api;

import com.eventflowerexchange.dto.request.AuthRequestDTO;
import com.eventflowerexchange.dto.request.ResetPasswordRequestDTO;
import com.eventflowerexchange.dto.request.UserRequestDTO;
import com.eventflowerexchange.dto.response.AuthResponseDTO;
import com.eventflowerexchange.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthAPI {
    private final UserService userService;

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

    @PostMapping("/password")
    public ResponseEntity<String> getUserIdByEmail(@Valid @RequestBody UserRequestDTO userRequestDTO) {
        String userID = userService.getUserIdByEmail(userRequestDTO.getEmail());
        return new ResponseEntity<>(userID, HttpStatus.OK);
    }

    @PutMapping("/password")
    public ResponseEntity<Object> updateUserPassword(
            @RequestBody ResetPasswordRequestDTO resetPasswordRequestDTO
            ) {
        userService.updatePasswordById(resetPasswordRequestDTO.getUserID(), resetPasswordRequestDTO.getPassword());
        return ResponseEntity.ok("Update password!");
    }
}
