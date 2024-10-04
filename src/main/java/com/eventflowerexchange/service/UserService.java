package com.eventflowerexchange.service;

import com.eventflowerexchange.dto.request.AuthRequestDTO;
import com.eventflowerexchange.dto.request.UpdateRequestDTO;
import com.eventflowerexchange.dto.request.UserRequestDTO;
import com.eventflowerexchange.dto.response.AuthResponseDTO;
import com.eventflowerexchange.entity.User;

public interface UserService {
    public String register(UserRequestDTO userRequestDTO);
    public AuthResponseDTO login(AuthRequestDTO authRequestDTO);
    public User updateUserById(String userID, UpdateRequestDTO updateRequestDTO)  ;
    public User findUserById(String userID);
}
