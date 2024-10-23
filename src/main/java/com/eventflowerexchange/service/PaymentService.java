package com.eventflowerexchange.service;

import com.eventflowerexchange.entity.Order;
import com.eventflowerexchange.entity.Payment;
import com.eventflowerexchange.entity.PaymentEnum;

public interface PaymentService {
    Payment createPayment(Order order, PaymentEnum paymentEnum);
    Payment getPaymentByOrderId(Long orderID);
}
