package com.eventflowerexchange.service.impl;

import com.eventflowerexchange.dto.request.LoginRequestDTO;
import com.eventflowerexchange.dto.request.UpdateRequestDTO;
import com.eventflowerexchange.dto.request.UserRequestDTO;
import com.eventflowerexchange.entity.User;
import com.eventflowerexchange.exception.DuplicateEntity;
import com.eventflowerexchange.mapper.UserMapper;
import com.eventflowerexchange.repository.UserRepository;
import com.eventflowerexchange.service.UserService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserMapper userMapper;

    @Override
    public User register(UserRequestDTO userRequestDTO) {
        // check if phone is existed
        if (userRepository.existsByPhone(userRequestDTO.getPhone())) {
            throw new DuplicateEntity("Duplicate phone!");
        }
        // check if email is existed
        if (userRepository.existsByEmail(userRequestDTO.getEmail())) {
//            User userExisted = userRepository.findUserByEmail(user.getEmail());
//            if (!userExisted.isActive()) {
//                return
//            }
            throw new DuplicateEntity("Duplicate email!");
        }
        User user = userMapper.toUser(userRequestDTO);
        // set other fields
        user.setRegisterDate(LocalDateTime.now());
        user.setRoleID(3);
        // Save account to DB
        return userRepository.save(user);
    }

    @Override
    public User login(LoginRequestDTO user) throws Exception{
        User isExistedUser = userRepository.findUserByEmail(user.getEmail());
        // Check if email is incorrect
        if (isExistedUser == null) {
            throw new EntityNotFoundException("Incorrect email!");
        }
        // Check if password is incorrect
        if (!isExistedUser.getPassword().equals(user.getPassword())) {
            throw new Exception("Incorrect password!");
        }
        // Check if user is not active
        if (!isExistedUser.isActive()){
            throw new Exception("User is not active!");
        }
        return isExistedUser;
    }

    @Override
    public User getUserByEmail(String email) {
        User user = userRepository.findUserByEmail(email);
        if (user == null) {
            throw new EntityNotFoundException("User not found!");
        }
        return user;
    }

    @Override
    public User updateUserByEmail(String email, UpdateRequestDTO updateRequestDTO) {
        User userUpdate = getUserByEmail(email);
        userMapper.updateUser(userUpdate, updateRequestDTO);
//        userUpdate.setAddress(updateRequestDTO.getAddress());
//        userUpdate.setFullName(updateRequestDTO.getFullName());
//        userUpdate.setPhone(updateRequestDTO.getPhone());
        return userRepository.save(userUpdate);
    }
}

