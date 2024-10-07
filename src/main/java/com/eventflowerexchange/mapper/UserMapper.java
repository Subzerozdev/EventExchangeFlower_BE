package com.eventflowerexchange.mapper;

import com.eventflowerexchange.dto.request.OrderRequestDTO;
import com.eventflowerexchange.dto.request.UpdateRequestDTO;
import com.eventflowerexchange.dto.request.UserRequestDTO;
import com.eventflowerexchange.entity.Order;
import com.eventflowerexchange.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface UserMapper {
    User toUser(UserRequestDTO requestDTO);
    void updateUser(@MappingTarget User user, UpdateRequestDTO requestDTO);
}
