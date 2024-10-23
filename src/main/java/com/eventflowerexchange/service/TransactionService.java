package com.eventflowerexchange.service;

import com.eventflowerexchange.entity.Order;

public interface TransactionService {
    void createTransactions(Order order);
}
