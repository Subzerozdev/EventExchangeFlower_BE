package com.eventflowerexchange.api;

import com.eventflowerexchange.dto.request.LoginRequestDTO;
import com.eventflowerexchange.dto.request.UpdateRequestDTO;
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

    @Autowired
    private UserService userService;

    @PutMapping("/{id}")
    public ResponseEntity<Object> updateUserProfile(
            @Valid @RequestBody UpdateRequestDTO updateRequestDTO,
            @PathVariable("id") String id)  {
        User user = userService.updateUserById(id, updateRequestDTO);
        return ResponseEntity.ok(user);
    }
}
