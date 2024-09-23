package com.eventflowerexchange.service.impl;

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
            throw new DuplicateEntity("Duplicate email!");
        }
        // set other fields
        user.setRegisterDate(LocalDateTime.now());
        user.setRoleID(3);
        user.setDeleted(false);
        // Save account to DB
        return userRepository.save(user);
    }

    @Override
    public User login(User user) throws Exception{
        User isExistedUser = userRepository.findUserByEmail(user.getEmail());
        // Check if email is incorrect
        if (isExistedUser == null) {
            throw new EntityNotFoundException("Incorrect email!");
        }
        // Check if password is incorrect
        if (!isExistedUser.getPassword().equals(user.getPassword())) {
            throw new Exception("Incorrect password!");
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
    public User updateUserById(Long id, UserRequestDTO userRequest) {
        User userUpdate = getUserById(id);
        userUpdate.setAddress(userRequest.getAddress());
        userUpdate.setFullName(userRequest.getFullName());
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

