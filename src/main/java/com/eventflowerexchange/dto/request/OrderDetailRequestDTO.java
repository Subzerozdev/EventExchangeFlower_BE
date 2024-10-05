package com.eventflowerexchange.dto.request;

import com.eventflowerexchange.entity.Order;
import com.eventflowerexchange.entity.Post;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderDetailRequestDTO {
    private int numberOfProducts;
//    private Long totalMoney;
    private Long postID;

//    @JsonProperty("order_id")
//    @Min(value=1, message = "Order's ID must be  >0 ")
//    private Long orderId;
}
