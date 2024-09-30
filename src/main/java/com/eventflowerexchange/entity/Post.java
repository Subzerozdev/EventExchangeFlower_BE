package com.eventflowerexchange.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "posts")
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name= "name", nullable = false,length = 350)
    private String  name;

    @Column(name = "description")
    private String description;


    @Column(name = "thumbnail", length = 300)
    private String thumbnail;

    @Column(name = "address")
    private String address;

    @Column(name ="start_date")
    private Date startDate;


    @Column(name ="end_date")
    private Date endDate;

    private Float price;

    @Column(name= "is_deleted")
    private boolean isDeleted ;


    @ManyToOne()
    @JoinColumn(name="category_id")
    private Category category;

    @ManyToOne()
    @JoinColumn(name="user_id")
    private User user;




}
