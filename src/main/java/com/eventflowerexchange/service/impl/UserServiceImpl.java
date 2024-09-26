package com.eventflowerexchange.service.impl;

import com.eventflowerexchange.dto.request.LoginRequestDTO;
import com.eventflowerexchange.dto.request.UpdateRequestDTO;
import com.eventflowerexchange.dto.request.UserRequestDTO;
import com.eventflowerexchange.entity.User;
import com.eventflowerexchange.exception.DuplicateEntity;
import com.eventflowerexchange.repository.UserRepository;
import com.eventflowerexchange.service.UserService;
import jakarta.persistence.EntityNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;

    @Override
    public User register(User user) {
        // check if phone is existed
        if (userRepository.existsByPhone(user.getPhone())) {
            throw new DuplicateEntity("Duplicate phone!");
        }
        // check if email is existed
        if (userRepository.existsByEmail(user.getEmail())) {
//            User userExisted = userRepository.findUserByEmail(user.getEmail());
//            if (!userExisted.isActive()) {
//                return
//            }
            throw new DuplicateEntity("Duplicate email!");
        }
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
    public User updateUserById(String email, UpdateRequestDTO updateRequestDTO) {
        User userUpdate = getUserByEmail(email);
        userUpdate.setAddress(updateRequestDTO.getAddress());
        userUpdate.setFullName(updateRequestDTO.getFullName());
        userUpdate.setPhone(updateRequestDTO.getPhone());
        return userRepository.save(userUpdate);
    }


    @Override
    public User getUserById(Long id) throws EntityNotFoundException {
        Optional<User> user = userRepository.findById(id);
        if (user.isEmpty()) {
            throw new EntityNotFoundException("User not found");
        }
        return user.get();
    }


}

