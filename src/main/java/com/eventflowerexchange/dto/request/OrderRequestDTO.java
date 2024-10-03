package com.eventflowerexchange.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderRequestDTO {
    @JsonProperty("order_id")
    @Min(value=1, message = "Order's ID must be  >0 ")
    private Long orderId;
}
