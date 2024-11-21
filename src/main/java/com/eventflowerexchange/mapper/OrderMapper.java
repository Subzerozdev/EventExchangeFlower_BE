package com.eventflowerexchange.mapper;

import com.eventflowerexchange.dto.request.OrderRequestDTO;
import com.eventflowerexchange.dto.response.OrderResponseDTO;
import com.eventflowerexchange.entity.Order;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface OrderMapper {
    @Mapping(target = "orderDetails", ignore = true)
    Order toOrder(OrderRequestDTO orderRequestDTO);
}
