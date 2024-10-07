package com.eventflowerexchange.service;

import com.eventflowerexchange.dto.request.ShopRequestDTO;
import com.eventflowerexchange.entity.Shop;
import com.eventflowerexchange.entity.User;

public interface ShopService {
    Shop createShop(User user, ShopRequestDTO shopRequestDTO);
    Shop updateShop(Long id, ShopRequestDTO shopRequestDTO);
    Shop getShop(Long id);
    void deleteShop(Long id);
}
