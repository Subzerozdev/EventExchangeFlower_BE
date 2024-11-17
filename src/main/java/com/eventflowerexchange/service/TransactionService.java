package com.eventflowerexchange.service;

import com.eventflowerexchange.entity.Order;

public interface TransactionService {
    void createTransaction01(Order order);
    void createTransaction02(Order order);
    boolean updateStatusFromAdminToSeller(Long transactionId);
}
