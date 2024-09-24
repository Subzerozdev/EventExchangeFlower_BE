package com.eventflowerexchange.service;

import com.eventflowerexchange.dto.request.LoginRequestDTO;
import com.eventflowerexchange.dto.request.UserRequestDTO;
import com.eventflowerexchange.entity.User;
import jakarta.persistence.EntityNotFoundException;

public interface UserService {
    public User register(User user);
    public User login(LoginRequestDTO user) throws Exception;
    public User updateUserById(Long id, UserRequestDTO userRequest);
    public User getUserByEmail(String email);
    public User getUserById(Long id) throws EntityNotFoundException;
}
