package com.eventflowerexchange.repository;

import com.eventflowerexchange.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface UserRepository extends JpaRepository<User, Long> {
    public User findUserByEmail(String email);
}

