package com.eventflowerexchange.dto.request;

import com.eventflowerexchange.entity.PaymentEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderRequestDTO {
    private String fullName;
    private String phoneNumber;
    private String email;
    private String address;
    private String note;
    private PaymentEnum paymentMethod;
    private Long totalMoney;
    private List<OrderDetailRequestDTO> orderDetails;
}
