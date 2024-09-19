package com.eventflowerexchange.service;

import com.eventflowerexchange.entity.User;
import com.eventflowerexchange.exception.DuplicateEntity;
import com.eventflowerexchange.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    public User register(User user) {
        try {
            User newUser = userRepository.save(user);  // Lưu account xuống database
            return newUser;
        } catch (Exception e) {
            e.printStackTrace();

            if (e.getMessage().contains(user.getEmail())) {
                throw new DuplicateEntity("Duplicate email!!!!");
            }
            if (e.getMessage().contains(user.getPhone())) {
                throw new DuplicateEntity("Duplicate phone!!!!");
            }

        }
return null;
    }

}

