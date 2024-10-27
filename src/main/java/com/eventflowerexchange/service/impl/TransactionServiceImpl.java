package com.eventflowerexchange.service.impl;

import com.eventflowerexchange.entity.*;
import com.eventflowerexchange.repository.TransactionRepository;
import com.eventflowerexchange.repository.UserRepository;
import com.eventflowerexchange.service.PaymentService;
import com.eventflowerexchange.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TransactionServiceImpl implements TransactionService {
    private final TransactionRepository transactionRepository;
    private final PaymentService paymentService;
    private final UserRepository userRepository;

    @Override
    public void createTransactions(Order order) {
        // Create Payment
        Payment payment = paymentService.getPaymentByOrderId(order.getId());
        // Create Transactions
        // Transaction 01: VNPAY to CUSTOMER
        User user = order.getUser();
        Transactions transactions01 = createTransaction(null, user, payment, TransactionsEnum.SUCCESS, "Nạp tiền VNPAY to Customer");
        // Transaction 02: CUSTOMER to ADMIN
        User admin = userRepository.findUserByEmail("hoaloicuofficial@gmail.com");
        Transactions transactions02 = createTransaction(user, admin, payment, TransactionsEnum.SUCCESS, "CUSTOMER TO ADMIN");
        // cái khúc này admin ăn tiền nè, nên sẽ có thêm field float thêm user và sau 1 đơn hàng sẽ cộng 20 phần trăm cho admin
        float newBalance = admin.getBalance() + order.getTotalMoney() * 0.20f;
        transactions02.setAmount(newBalance);   // code mới thêm
        admin.setBalance(newBalance);
        // Transaction 03: ADMIN TO SELLER
        User owner = order.getOrderDetails().get(0).getPost().getUser();
        Transactions transactions03 = createTransaction(owner, admin, payment, TransactionsEnum.SUCCESS, "ADMIN TO OWNER");
        // cái khúc này admin ăn tiền nè, nên sẽ có thêm field float thêm user và sau 1 đơn hàng sẽ cộng 20 phần trăm cho admin
        float newShopBalance = owner.getBalance() + order.getTotalMoney() * 0.8f;
        transactions03.setAmount(newShopBalance);
        owner.setBalance(newShopBalance);
        // Save balance to seller and admin
        userRepository.save(admin);
        userRepository.save(owner);
        // Save transactions
        transactionRepository.save(transactions01);
        transactionRepository.save(transactions02);
        transactionRepository.save(transactions03);
    }

    private Transactions createTransaction(User userFrom, User userTo
            , Payment payment, TransactionsEnum transactionsEnum, String description) {
        return Transactions.builder()
                .from(userFrom)
                .to(userTo)
                .payment(payment)
                .status(transactionsEnum)
                .description(description)
                .build();
    }
}
