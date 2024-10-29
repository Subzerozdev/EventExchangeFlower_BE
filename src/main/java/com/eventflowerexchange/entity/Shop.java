package com.eventflowerexchange.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Shop {
    @JsonIgnore
    @Id
    @GeneratedValue(strategy = GenerationType.UUID )
    private String id;

    private String shopName;
    private String description;
    private String shopAddress;

    @JsonIgnore
    @OneToOne
    private User user;
    // 1 shop có thể có nhiều feedbacks
    @OneToMany(mappedBy = "shop")
    Set<Feedback> shop_feedbacks;
}
