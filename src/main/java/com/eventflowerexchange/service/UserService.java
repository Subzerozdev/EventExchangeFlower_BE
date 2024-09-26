package com.eventflowerexchange.service;

import com.eventflowerexchange.dto.request.LoginRequestDTO;
import com.eventflowerexchange.dto.request.UpdateRequestDTO;
import com.eventflowerexchange.dto.request.UserRequestDTO;
import com.eventflowerexchange.entity.User;
import jakarta.persistence.EntityNotFoundException;

public interface UserService {
    public User register(UserRequestDTO userRequestDTO);
    public User login(LoginRequestDTO loginRequestDTO) throws Exception;
    public User updateUserByEmail(String email, UpdateRequestDTO updateRequestDTO)  ;
    public User getUserByEmail(String email);
}
