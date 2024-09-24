package com.eventflowerexchange.service;

import com.eventflowerexchange.dto.request.LoginRequestDTO;
import com.eventflowerexchange.dto.request.UpdateRequestDTO;
import com.eventflowerexchange.dto.request.UserRequestDTO;
import com.eventflowerexchange.entity.User;
import jakarta.persistence.EntityNotFoundException;

public interface UserService {
    public User register(User user);
    public User login(LoginRequestDTO loginRequestDTO) throws Exception;
    public User updateUserById(Long id, UpdateRequestDTO updateRequestDTO)  ;
    public User getUserByEmail(String email);
    public User getUserById(Long id) throws EntityNotFoundException;
}
