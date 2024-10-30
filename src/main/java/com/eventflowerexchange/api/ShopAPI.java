package com.eventflowerexchange.api;

import com.eventflowerexchange.dto.request.ShopRequestDTO;
import com.eventflowerexchange.entity.Shop;
import com.eventflowerexchange.entity.USER_ROLE;
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
    public ResponseEntity<Object> createSellerShop(
            @RequestBody ShopRequestDTO shopRequestDTO,
            @RequestHeader("Authorization") String jwt
    ) {
        User user = jwtService.getUserFromJwtToken(jwt);
        if (user.getRole().equals(USER_ROLE.ROLE_CUSTOMER)) {
            userService.updateRole(user);
            Shop shop = shopService.createSellerShop(user, shopRequestDTO);
            return ResponseEntity.ok(shop);
        }
        return ResponseEntity.badRequest().build();
    }

    @GetMapping("/seller/shop")
    public ResponseEntity<Object> getShopBySellerId(
            @RequestHeader("Authorization") String jwt
    ) {
        User user = jwtService.getUserFromJwtToken(jwt);
        Shop shop = shopService.getSellerShop(user);
        return ResponseEntity.ok(shop);
    }

    @PutMapping("/seller/shop")
    public ResponseEntity<Object> updateSellerShop(
            @RequestBody ShopRequestDTO shopRequestDTO,
            @RequestHeader("Authorization") String jwt
    ) {
        User user = jwtService.getUserFromJwtToken(jwt);
        Shop shop = shopService.updateSellerShop(user, shopRequestDTO);
        return ResponseEntity.ok(shop);
    }

    // User can view shop of seller by order id
    // FE not implement (because of time)
//    @GetMapping("/order/shop/{id}")
//    public ResponseEntity<Object> getSellerShopByOrderId(
//            @PathVariable Long id
//    ) {
//        String shopId = shopService.getShopIdByOrderId(id);
//        return ResponseEntity.ok(shopId);
//    }

    // User can view shop of seller by seller id (fe get from post)
    // FE not implement (because of time)
//    @GetMapping("/shop/posts/{id}")
//    public ResponseEntity<Object> getShopPosts(
//            @PathVariable String id
//    ) {
//        Shop shop = shopService.getShopById(id);
//        List<Post> posts = postService.getSellerPosts(shop.getUser().getId());
//        return new ResponseEntity<>(posts, HttpStatus.OK);
//    }
}
