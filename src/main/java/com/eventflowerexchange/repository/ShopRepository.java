package com.eventflowerexchange.repository;

import com.eventflowerexchange.entity.Shop;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShopRepository extends JpaRepository<Shop, Long> {
    Shop findShopById(long id);
}
