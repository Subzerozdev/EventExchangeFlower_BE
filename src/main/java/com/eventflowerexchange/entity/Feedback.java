package com.eventflowerexchange.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name ="feedback")
public class Feedback {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String content;
    private  int rating;

    // 1 account sẽ có nhiều feedback.
    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "customer_id")
    User customer;

    // 1 account có thể feedback cho nhiều shop

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "shop_id")
    Shop shop;

}
