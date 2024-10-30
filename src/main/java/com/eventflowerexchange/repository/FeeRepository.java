package com.eventflowerexchange.repository;

import com.eventflowerexchange.entity.Fee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FeeRepository extends JpaRepository<Fee, Integer> {
    Fee getFeeById(int id);
}
