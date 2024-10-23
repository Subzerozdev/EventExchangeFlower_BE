package com.eventflowerexchange.repository;

import com.eventflowerexchange.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepository  extends JpaRepository<Payment,Long> {
    Payment findPaymentByOrderId(Long orderID);
}
