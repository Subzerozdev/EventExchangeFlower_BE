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
@RequestMapping("/api/shop")
@RequiredArgsConstructor
public class ShopAPI {
    private final ShopService shopService;
    private final JwtService jwtService;
    private final UserService userService;

    @PostMapping("")
    public ResponseEntity<Object> createShop(
            @RequestBody ShopRequestDTO shopRequestDTO,
            @RequestHeader("Authorization") String jwt
    ) {
        User user = jwtService.getUserFromJwtToken(jwt);
        userService.updateRole(user);
        Shop shop = shopService.createShop(user, shopRequestDTO);
        return ResponseEntity.ok(shop);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getShopById(@PathVariable Long id) {
        Shop shop = shopService.getShop(id);
        return ResponseEntity.ok(shop);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> updateShop(@PathVariable Long id, @RequestBody ShopRequestDTO shopRequestDTO) {
        Shop shop = shopService.updateShop(id, shopRequestDTO);
        return ResponseEntity.ok(shop);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteShop(@PathVariable Long id) {
        shopService.deleteShop(id);
        return ResponseEntity.ok().build();
    }
}
