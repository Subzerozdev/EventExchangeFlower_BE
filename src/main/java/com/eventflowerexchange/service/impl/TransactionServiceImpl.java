package com.eventflowerexchange.service.impl;

import com.eventflowerexchange.entity.*;
import com.eventflowerexchange.repository.TransactionRepository;
import com.eventflowerexchange.repository.UserRepository;
import com.eventflowerexchange.service.FeeService;
import com.eventflowerexchange.service.PaymentService;
import com.eventflowerexchange.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class TransactionServiceImpl implements TransactionService {
    private final PaymentService paymentService;
    private final FeeService feeService;
    private final UserRepository userRepository;
    private final TransactionRepository transactionRepository;

    @Override
    public void createTransactions(Order order) {
        // Create Payment
        Payment payment = paymentService.getPaymentByOrderId(order.getId());
        // Get Platform fee
        float feeAmount = feeService.getFeeAmountById(order.getFeeId());
        // Create Transactions
        // Transaction 01: VNPAY to CUSTOMER
        User user = order.getUser();
        Transactions transactions01 = createTransaction(null, user, payment, TransactionsEnum.SUCCESS, "Nạp tiền VNPAY to Customer");
        transactionRepository.save(transactions01);
        // Transaction 02: CUSTOMER to ADMIN
        User admin = userRepository.findUserByEmail("hoaloicuofficial@gmail.com");
        Transactions transactions02 = createTransaction(user, admin, payment, TransactionsEnum.SUCCESS, "CUSTOMER TO ADMIN");
        // cái khúc này admin ăn tiền nè, nên sẽ có thêm field float thêm user và sau 1 đơn hàng sẽ cộng 20 phần trăm cho admin
        float newBalance = admin.getBalance() + order.getTotalMoney() * feeAmount;
        transactions02.setAmount(order.getTotalMoney() * feeAmount);
        transactionRepository.save(transactions02);
        admin.setBalance(newBalance);
        userRepository.save(admin);
        // Transaction 03: ADMIN TO SELLER
        User owner = order.getOrderDetails().get(0).getPost().getUser();
        Transactions transaction03 = createTransaction(admin, owner, payment, TransactionsEnum.SUCCESS, "ADMIN TO OWNER");
        float newShopBalance = owner.getBalance() + order.getTotalMoney() * (1.0f-feeAmount);
        transaction03.setAmount(order.getTotalMoney() * (1.0f-feeAmount));
        transactionRepository.save(transaction03);
        owner.setBalance(newShopBalance);
        userRepository.save(owner);
    }

    private Transactions createTransaction(User userFrom, User userTo
            , Payment payment, TransactionsEnum transactionsEnum, String description) {
        return Transactions.builder()
                .from(userFrom)
                .to(userTo)
                .payment(payment)
                .status(transactionsEnum)
                .createAt(LocalDateTime.now())
                .description(description)
                .build();
    }
}
