package com.eventflowerexchange.mapper;

import com.eventflowerexchange.dto.request.ShopRequestDTO;
import com.eventflowerexchange.entity.Shop;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface ShopMapper {
    Shop toShop(ShopRequestDTO shopRequestDTO);
    void updateShop(@MappingTarget Shop shop, ShopRequestDTO shopRequestDTO);
}
