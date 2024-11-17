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
    public void createTransaction01(Order order) {
        float feeAmount = feeService.getFeeAmountById(order.getFeeId());
        User customer = order.getUser();
        User admin = userRepository.findUserByEmail("hoaloicuofficial@gmail.com");
        Transactions transaction = createTransaction(order, customer, admin, "CUSTOMER TO ADMIN", TRANSACTION_STATUS.SUCCESS);
        float newBalance = admin.getBalance() + order.getTotalMoney() * feeAmount;
        transaction.setAmount(order.getTotalMoney() * feeAmount);
        transactionRepository.save(transaction);
        admin.setBalance(newBalance);
        userRepository.save(admin);
    }

    @Override
    public void createTransaction02(Order order) {
        float feeAmount = feeService.getFeeAmountById(order.getFeeId());
        User admin = userRepository.findUserByEmail("hoaloicuofficial@gmail.com");
        User owner = order.getOrderDetails().get(0).getPost().getUser();
        Transactions transaction = createTransaction(order, admin, owner, "ADMIN TO OWNER", TRANSACTION_STATUS.PENDING);
        float newShopBalance = owner.getBalance() + order.getTotalMoney() * (1.0f-feeAmount);
        transaction.setAmount(order.getTotalMoney() * (1.0f-feeAmount));
        transactionRepository.save(transaction);
        owner.setBalance(newShopBalance);
        userRepository.save(owner);
    }

    @Override
    public boolean updateStatusFromAdminToSeller(Long transactionId) {
        boolean result = false;
        Transactions transaction = transactionRepository.findTransactionsById(transactionId);
        if(transaction.getOrder().getStatus().equals(ORDER_STATUS.COMPLETED)){
            transaction.setStatus(TRANSACTION_STATUS.SUCCESS);
            transactionRepository.save(transaction);
            result = true;
        }
        return result;
    }

    private Transactions createTransaction(Order order, User userFrom, User userTo
            , String description, TRANSACTION_STATUS status) {
        return Transactions.builder()
                .order(order)
                .from(userFrom)
                .to(userTo)
                .status(status)
                .createAt(LocalDateTime.now())
                .description(description)
                .build();
    }
}
