package com.eventflowerexchange.repository;

import com.eventflowerexchange.entity.Transactions;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRepository extends JpaRepository<Transactions, Long> {
}
