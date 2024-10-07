package com.eventflowerexchange.service.impl;

import com.eventflowerexchange.dto.request.ShopRequestDTO;
import com.eventflowerexchange.entity.Shop;
import com.eventflowerexchange.entity.User;
import com.eventflowerexchange.mapper.ShopMapper;
import com.eventflowerexchange.repository.ShopRepository;
import com.eventflowerexchange.repository.UserRepository;
import com.eventflowerexchange.service.ShopService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ShopServiceImpl implements ShopService {
    private final ShopRepository shopRepository;
    private final ShopMapper shopMapper;

    @Override
    public Shop createShop(User user, ShopRequestDTO shopRequestDTO) {
        Shop shop = shopMapper.toShop(shopRequestDTO);
        shop.setUser(user);
        shopRepository.save(shop);
        return shop;
    }

    @Override
    public Shop updateShop(Long id, ShopRequestDTO shopRequestDTO) {
        Shop shop = shopRepository.findShopById(id);
        shopMapper.updateShop(shop, shopRequestDTO);
        shopRepository.save(shop);
        return shop;
    }

    @Override
    public Shop getShop(Long id) {
        return shopRepository.findShopById(id);
    }

    @Override
    public void deleteShop(Long id) {
        Shop shop = shopRepository.findShopById(id);
        shopRepository.delete(shop);
    }
}
