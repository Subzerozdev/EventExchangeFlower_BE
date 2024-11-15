package com.eventflowerexchange.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "transactions")
public class Transactions {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "from_id")
    private User from;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "to_id")
    private User to;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;

    private TRANSACTION_STATUS status;
    private String description;
    private float amount;

    @JsonProperty("create_at")
    private LocalDateTime createAt;
}
