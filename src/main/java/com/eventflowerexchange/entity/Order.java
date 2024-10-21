package com.eventflowerexchange.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Pattern(regexp = "(84|0[3|5|7|8|9])+(\\d{8})", message = "Invalid phone !!!!!")
    private String phoneNumber;

    @Email(message = " Invalid Email !!!! ")
    private String email;

    private String fullName;
    private String address;
    private String note;
    private Long totalMoney;
    private LocalDateTime orderDate;
    private ORDER_STATUS status;

    @JsonIgnore
    @ManyToOne
    private User user;

    @JsonIgnore
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderDetail> orderDetails;

    @JsonIgnore
    @OneToOne(mappedBy = "order")
    Payment payment;
}
