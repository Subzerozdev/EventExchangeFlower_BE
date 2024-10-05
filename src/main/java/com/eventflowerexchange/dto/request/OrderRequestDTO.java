package com.eventflowerexchange.dto.request;

import com.eventflowerexchange.entity.User;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
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
    private String paymentMethod;
    private Long totalMoney;
    private List<OrderDetailRequestDTO> orderDetails;
}
