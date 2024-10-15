package com.eventflowerexchange.repository;

import com.eventflowerexchange.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, String> {
    boolean existsByPhone(String phone);
    boolean existsByEmail(String email);
    User findUserByEmail(String email);
    User findUserById(String id);
}

