package com.eventflowerexchange.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "payment")
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    @JsonProperty("create_at")
    private LocalDateTime createAt;

    private PaymentEnum payment_Method;

    @OneToOne
    @JoinColumn(name = "order_id")
    Order order;

    @OneToMany(mappedBy = "payment")
    Set<Transactions> transactions;
}
