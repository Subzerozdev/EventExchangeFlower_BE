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
        try {
            user.setRegisterDate(LocalDateTime.now());
            user.setRoleID(3);
            // Save account to DB
            User newUser = userRepository.save(user);
            return newUser;
        } catch (Exception e) {
            if (e.getMessage().contains(user.getEmail())) {
                throw new DuplicateEntity("Duplicate email!!!!");
            }
            if (e.getMessage().contains(user.getPhone())) {
                throw new DuplicateEntity("Duplicate phone!!!!");
            }
        }
        return null;
    }

    @Override
    public User login(User user) {
        User isExistedUser = userRepository.findUserByEmail(user.getEmail());
        if (isExistedUser == null) {
            return null;
        }
        return isExistedUser;
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

