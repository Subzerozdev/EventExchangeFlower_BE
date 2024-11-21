package com.eventflowerexchange.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "application")
public class Application {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String problem;
    private String content;
    private APPLICATION_STATUS status;

    @Column(name = "order_id")
    private Long orderID;
    @Column(name = "type_id")
    private APPLICATION_TYPE type;

    @JsonIgnore
    @ManyToOne
    private User user;
}
