package com.eventflowerexchange.service.impl;

import com.eventflowerexchange.entity.*;
import com.eventflowerexchange.repository.TransactionRepository;
import com.eventflowerexchange.repository.UserRepository;
import com.eventflowerexchange.service.FeeService;
import com.eventflowerexchange.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class TransactionServiceImpl implements TransactionService {
    private final FeeService feeService;
    private final UserRepository userRepository;
    private final TransactionRepository transactionRepository;

    @Override
    public void createTransactions(Order order) {
        // Get Platform fee
        float feeAmount = feeService.getFeeAmountById(order.getFeeId());
        // Transaction 01: CUSTOMER to ADMIN
        User customer = order.getUser();
        User admin = userRepository.findUserByEmail("hoaloicuofficial@gmail.com");
        Transactions transactions02 = createTransaction(customer, admin, "CUSTOMER TO ADMIN");
        float newBalance = admin.getBalance() + order.getTotalMoney() * feeAmount;
        transactions02.setAmount(order.getTotalMoney() * feeAmount);
        transactionRepository.save(transactions02);
        admin.setBalance(newBalance);
        userRepository.save(admin);
    }

    // Transaction 02: ADMIN TO SELLER
    @Override
    public void setTransaction03(Order order) {
        // Get Platform fee
        float feeAmount = feeService.getFeeAmountById(order.getFeeId());
        // Create transaction03
        User admin = userRepository.findUserByEmail("hoaloicuofficial@gmail.com");
        User owner = order.getOrderDetails().get(0).getPost().getUser();
        Transactions transaction03 = createTransaction(admin, owner, "ADMIN TO OWNER");
        float newShopBalance = owner.getBalance() + order.getTotalMoney() * (1.0f-feeAmount);
        transaction03.setAmount(order.getTotalMoney() * (1.0f-feeAmount));
        transactionRepository.save(transaction03);
        owner.setBalance(newShopBalance);
        userRepository.save(owner);
    }

    private Transactions createTransaction(User userFrom, User userTo
            , String description) {
        return Transactions.builder()
                .from(userFrom)
                .to(userTo)
                .status(TransactionsEnum.SUCCESS)
                .createAt(LocalDateTime.now())
                .description(description)
                .build();
    }
}
