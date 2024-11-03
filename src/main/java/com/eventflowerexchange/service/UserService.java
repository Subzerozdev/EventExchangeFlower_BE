package com.eventflowerexchange.service;

import com.eventflowerexchange.dto.request.AuthRequestDTO;
import com.eventflowerexchange.dto.request.UpdateRequestDTO;
import com.eventflowerexchange.dto.request.UserRequestDTO;
import com.eventflowerexchange.dto.response.AuthResponseDTO;
import com.eventflowerexchange.entity.User;

import java.util.List;

public interface UserService {
    String register(UserRequestDTO userRequestDTO);
    AuthResponseDTO login(AuthRequestDTO authRequestDTO);
    String getUserIdByEmail(String email);
    User updateUserById(String userID, UpdateRequestDTO updateRequestDTO);
    User findUserById(String userID);
    void updatePasswordById(String userID, String password);
    void updateRole(User user);
    AuthResponseDTO updateSellerToken(User user);
    void deleteUserById(String userID);
    List<User> getUsers();
}
