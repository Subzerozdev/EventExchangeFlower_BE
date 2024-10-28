package com.eventflowerexchange.service.impl;

import com.eventflowerexchange.entity.*;
import com.eventflowerexchange.repository.TransactionRepository;
import com.eventflowerexchange.repository.UserRepository;
import com.eventflowerexchange.service.PaymentService;
import com.eventflowerexchange.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

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
        float newBalance = admin.getBalance() + order.getTotalMoney() * 0.15f;
        transactions02.setAmount(newBalance);   // code mới thêm
        admin.setBalance(newBalance);
        userRepository.save(admin);
        // Transaction 03: ADMIN TO SELLER
        order.getOrderDetails().forEach(orderDetail -> {
            User owner = orderDetail.getPost().getUser();
            Transactions transactions03 = createTransaction(admin, owner, payment, TransactionsEnum.SUCCESS, "ADMIN TO OWNER OF POST: "+orderDetail.getPost().getName());
            // cái khúc này admin ăn tiền nè, nên sẽ có thêm field float thêm user và sau 1 đơn hàng sẽ cộng 20 phần trăm cho admin
            float newShopBalance = owner.getBalance() + orderDetail.getPost().getPrice() * 0.85f;
            transactions03.setAmount(newShopBalance);
            owner.setBalance(newShopBalance);
            transactionRepository.save(transactions03);
            userRepository.save(owner);
        });
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
