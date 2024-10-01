package com.eventflowerexchange.repository;

import com.eventflowerexchange.entity.User;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, String> {
    public boolean existsByPhone(String phone);
    public boolean existsByEmail(String email);
    public User findUserByEmail(String email);
    public User findUserById(String id);
}

