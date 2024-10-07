package com.eventflowerexchange.api;

import com.eventflowerexchange.dto.request.ShopRequestDTO;
import com.eventflowerexchange.entity.Shop;
import com.eventflowerexchange.entity.User;
import com.eventflowerexchange.service.JwtService;
import com.eventflowerexchange.service.ShopService;
import com.eventflowerexchange.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ShopAPI {
    private final ShopService shopService;
    private final JwtService jwtService;
    private final UserService userService;

    @PostMapping("/shop")
    public ResponseEntity<Object> createShop(
            @RequestBody ShopRequestDTO shopRequestDTO,
            @RequestHeader("Authorization") String jwt
    ) {
        User user = jwtService.getUserFromJwtToken(jwt);
        userService.updateRole(user);
        Shop shop = shopService.createShop(user, shopRequestDTO);
        return ResponseEntity.ok(shop);
    }

    @GetMapping("/seller/shop")
    public ResponseEntity<Object> getShopById(@RequestHeader("Authorization") String jwt) {
        User user = jwtService.getUserFromJwtToken(jwt);
        Shop shop = shopService.getShop(user);
        return ResponseEntity.ok(shop);
    }

    @PutMapping("/seller/shop")
    public ResponseEntity<Object> updateShop(
            @RequestBody ShopRequestDTO shopRequestDTO,
            @RequestHeader("Authorization") String jwt
    ) {
        User user = jwtService.getUserFromJwtToken(jwt);
        Shop shop = shopService.updateShop(user, shopRequestDTO);
        return ResponseEntity.ok(shop);
    }
}
