package com.eventflowerexchange.service.impl;

import com.eventflowerexchange.dto.request.ShopRequestDTO;
import com.eventflowerexchange.entity.*;
import com.eventflowerexchange.mapper.ShopMapper;
import com.eventflowerexchange.repository.ShopRepository;
import com.eventflowerexchange.service.OrderService;
import com.eventflowerexchange.service.ShopService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ShopServiceImpl implements ShopService {
    private final ShopRepository shopRepository;
    private final ShopMapper shopMapper;
    private final OrderService orderService;

    @Override
    public Shop createSellerShop(User user, ShopRequestDTO shopRequestDTO) {
        Shop shop = shopMapper.toShop(shopRequestDTO);
        shop.setUser(user);
        shopRepository.save(shop);
        return shop;
    }

    @Override
    public Shop updateSellerShop(User user, ShopRequestDTO shopRequestDTO) {
        Shop shop = shopRepository.findShopByUser(user);
        shopMapper.updateShop(shop, shopRequestDTO);
        shopRepository.save(shop);
        return shop;
    }

    @Override
    public Shop getSellerShop(User user) {
        return shopRepository.findShopByUser(user);
    }

    @Override
    public String getShopIdByOrderId(Long orderId) {
        Order order = orderService.getOrderById(orderId);
        OrderDetail orderDetail = order.getOrderDetails().get(0);
        Post post = orderDetail.getPost();
        return shopRepository.findShopByUser(post.getUser()).getId();
    }

}
