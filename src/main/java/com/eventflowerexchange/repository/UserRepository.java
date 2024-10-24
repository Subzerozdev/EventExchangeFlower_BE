package com.eventflowerexchange.repository;

import com.eventflowerexchange.entity.USER_ROLE;
import com.eventflowerexchange.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserRepository extends JpaRepository<User, String> {
    boolean existsByPhone(String phone);
    boolean existsByEmail(String email);
    User findUserByEmail(String email);
    User findUserById(String id);
    @Query("select count(a)  from  User a where a.role =:role ")
    Long countByRole(@Param("role") USER_ROLE role);
}

