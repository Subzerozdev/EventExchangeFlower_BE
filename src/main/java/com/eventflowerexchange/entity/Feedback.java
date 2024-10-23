package com.eventflowerexchange.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name ="feedback")
public class Feedback {
    @JsonIgnore
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String content;
    private int rating;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "customer_id")
    User customer;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "shop_id")
    Shop shop;

}
