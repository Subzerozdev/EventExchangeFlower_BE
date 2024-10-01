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

    @Size(min = 3, message = "Password must be  at least 3 character !!!!!")
    private String password;

    private String fullName;
    private String address;

    @Pattern(regexp = "(84|0[3|5|7|8|9])+(\\d{8})", message = "Invalid phone !!!!!")
    @Column(unique = true)
    private String phone;

    @JsonIgnore
    @DateTimeFormat
    private LocalDateTime createdAt;

    @JsonIgnore
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

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Post> posts;
}
