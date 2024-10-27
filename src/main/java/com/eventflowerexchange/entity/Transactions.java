package com.eventflowerexchange.entity;

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
    Long id;

    @ManyToOne
    @JoinColumn(name = "from_id")
    User from;

    @ManyToOne
    @JoinColumn(name = "to_id")
    User to;

    @ManyToOne
    @JoinColumn(name = "payment_id")
    Payment payment;

    private TransactionsEnum status;

    private String description;

    private float amount;

    @JsonProperty("create_at")
    private LocalDateTime createAt;



}
