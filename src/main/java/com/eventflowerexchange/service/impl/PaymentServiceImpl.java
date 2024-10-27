package com.eventflowerexchange.service.impl;

import com.eventflowerexchange.entity.Order;
import com.eventflowerexchange.entity.Payment;
import com.eventflowerexchange.entity.PaymentEnum;
import com.eventflowerexchange.repository.PaymentRepository;
import com.eventflowerexchange.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {
    private final PaymentRepository paymentRepository;

    @Override
    public Payment createPayment(Order order, PaymentEnum paymentEnum) {
        Payment payment = Payment.builder()
                .order(order)
                .paymentMethod(paymentEnum)
                .createAt(LocalDateTime.now())
                .total(order.getTotalMoney())
                .build();
        return paymentRepository.save(payment);
    }

    @Override
    public Payment getPaymentByOrderId(Long orderID) {
        return paymentRepository.findPaymentByOrderId(orderID);
    }
}
