package com.eventflowerexchange.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;


@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "users")
public class User {
    @JsonIgnore
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Email(message = " Invalid Email !!!! ")
    @Column(unique = true)
    private String email;

    @JsonIgnore
    @Size(min = 8, message = "Password must be  at least 8 character !!!!!")
    private String password;

    private String fullName;
    private String address;

    @Pattern(regexp = "(^0(3|5|7|8|9)[0-9]{8,9}$)", message = "Invalid phone !!!!!")
    @Column(unique = true)
    private String phone;

    @DateTimeFormat
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @DateTimeFormat
    private LocalDateTime updatedAt;

    @JsonIgnore
    private boolean isActive;

    @Column(name = "role_id")
    private USER_ROLE role;

    @Column(name = "date_of_birth")
    private Date dob;

    @Column(name = "facebook_account_id")
    private int facebookAccountId;

    @Column(name = "google_account_id")
    private int googleAccountId;

    @JsonIgnore
    @OneToOne(mappedBy = "user")
    private OTPEmail otpEmail;

    @JsonIgnore
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Post> posts;

    @JsonIgnore
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Order> orders;

    @JsonIgnore
    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private RefreshToken refreshToken;
}
