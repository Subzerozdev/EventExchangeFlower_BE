package com.eventflowerexchange.api;

import com.eventflowerexchange.dto.request.UpdateRequestDTO;
import com.eventflowerexchange.dto.response.AuthResponseDTO;
import com.eventflowerexchange.entity.USER_ROLE;
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

    @PostMapping("/token")
    public ResponseEntity<Object> updateSellerToken(
            @RequestHeader("Authorization") String jwt
    ){
        User user = jwtService.getUserFromJwtToken(jwt);
        if (user.getRole().equals(USER_ROLE.ROLE_SELLER)){
            AuthResponseDTO authResponseDTO = userService.updateSellerToken(user);
            return ResponseEntity.ok(authResponseDTO);
        }
        return ResponseEntity.ok("Cannot get token!");
    }
}
