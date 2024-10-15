package com.eventflowerexchange.dto.response;

import lombok.*;

import java.io.Serializable;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PaymentResponseDTO implements Serializable {
    private String status;
    private String message;
    private String URL;
}
