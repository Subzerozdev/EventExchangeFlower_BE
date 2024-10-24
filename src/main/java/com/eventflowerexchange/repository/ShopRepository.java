package com.eventflowerexchange.repository;

import com.eventflowerexchange.entity.Shop;
import com.eventflowerexchange.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShopRepository extends JpaRepository<Shop, String> {
    Shop findShopByUser(User user);
}
