package com.eventflowerexchange.mapper;

import com.eventflowerexchange.dto.request.OrderRequestDTO;
import com.eventflowerexchange.dto.request.UpdateRequestDTO;
import com.eventflowerexchange.entity.Order;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface OrderMapper {
    // Order
    Order toOrder(OrderRequestDTO requestDTO);
    void updateOrder(@MappingTarget Order order, OrderRequestDTO requestDTO);
}
