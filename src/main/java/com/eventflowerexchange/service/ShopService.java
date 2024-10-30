package com.eventflowerexchange.service;

import com.eventflowerexchange.dto.request.ShopRequestDTO;
import com.eventflowerexchange.entity.Shop;
import com.eventflowerexchange.entity.User;

public interface ShopService {
    Shop createSellerShop(User user, ShopRequestDTO shopRequestDTO);
    Shop updateSellerShop(User user, ShopRequestDTO shopRequestDTO);
    Shop getSellerShop(User user);
//    String getShopIdByOrderId(Long orderId);
//    Shop getShopById(String shopId);
}
