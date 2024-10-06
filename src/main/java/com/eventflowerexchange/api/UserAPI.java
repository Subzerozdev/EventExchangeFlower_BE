package com.eventflowerexchange.api;

import com.eventflowerexchange.dto.request.UpdateRequestDTO;
import com.eventflowerexchange.entity.User;
import com.eventflowerexchange.service.JwtService;
import com.eventflowerexchange.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserAPI {
    private final UserService userService;
    private final JwtService jwtService;

    @PutMapping("")
    public ResponseEntity<Object> updateUserProfile(
            @Valid @RequestBody UpdateRequestDTO updateRequestDTO,
            @RequestHeader("Authorization") String jwt) {
        String userID = jwtService.getUserIdFromJwtToken(jwt);
        User user = userService.updateUserById(userID, updateRequestDTO);
        return ResponseEntity.ok(user);
    }

    @PostMapping("/password")
    public ResponseEntity<Object> updateUserPassword(
            @RequestBody String password,
            @RequestHeader("Authorization") String jwt
    ) {
        String userID = jwtService.getUserIdFromJwtToken(jwt);
        userService.updatePasswordById(userID, password);
        return ResponseEntity.ok("Update password!");
    }

    @PutMapping("/upgrade")
    public ResponseEntity<Object> updateUserRole(
            @RequestBody String role,
            @RequestHeader("Authorization") String jwt
    ){
        String userID = jwtService.getUserIdFromJwtToken(jwt);

        return ResponseEntity.ok(("User upgrade"));
    }
}
