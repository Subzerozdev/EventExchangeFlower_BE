package com.eventflowerexchange.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
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
@Table(name = "posts")
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 350)
    private String name;

    private String description;

    @Column(length = 300)
    private String thumbnail;

    private String address;

    private Long price;
    @JsonProperty("start_date")
    private LocalDateTime startDate;
    @JsonProperty("end_date")
    private LocalDateTime endDate;

    private POST_STATUS status;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @JsonIgnore
    @OneToMany(mappedBy = "post")
    private List<OrderDetail> orderDetails;

    @ManyToMany
    @JoinTable(name ="typeandpost",inverseJoinColumns = @JoinColumn(name="type_id", nullable = false),
            joinColumns = @JoinColumn(name = "post_id", nullable = false))
    private List<Type> types;

    @JsonProperty("imageUrls")
    @OneToMany(mappedBy = "post")
    private List<PostImage> images;

}
