package com.eventflowerexchange.service;

import com.eventflowerexchange.entity.Order;
import com.eventflowerexchange.entity.Transactions;
import com.eventflowerexchange.entity.User;

public interface TransactionService {
    void createTransaction01(Order order);
    void createTransaction02(Order order);
    boolean updateStatusFromAdminToSeller(Long transactionId);
    Transactions getTransactionsFromAdmin(Order order, User user);
}
