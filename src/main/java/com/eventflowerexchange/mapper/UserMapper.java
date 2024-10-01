package com.eventflowerexchange.mapper;

import com.eventflowerexchange.dto.request.UpdateRequestDTO;
import com.eventflowerexchange.dto.request.UserRequestDTO;
import com.eventflowerexchange.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface UserMapper {
    // User
    User toUser(UserRequestDTO requestDTO);
    void updateUser(@MappingTarget User user, UpdateRequestDTO requestDTO);
}
