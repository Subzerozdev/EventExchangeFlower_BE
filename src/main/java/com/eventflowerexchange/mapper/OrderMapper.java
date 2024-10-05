package com.eventflowerexchange.mapper;

import com.eventflowerexchange.dto.request.OrderRequestDTO;
import com.eventflowerexchange.entity.Order;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface OrderMapper {
    // Order
    @Mapping(target = "orderDetails", ignore = true)
    Order toOrder(OrderRequestDTO orderRequestDTO);
    void updateOrder(@MappingTarget Order order, OrderRequestDTO requestDTO);
}
