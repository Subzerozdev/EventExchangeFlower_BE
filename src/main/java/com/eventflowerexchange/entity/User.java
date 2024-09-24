package com.eventflowerexchange.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;


@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
    @JsonIgnore
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Email(message = " Invalid Email !!!! ")
    @Column(unique = true)
    private String email;

    @Size(min = 3, message = "Password must be  at least 3 character !!!!!")
    private String password;

    private String fullName;

    @Pattern(regexp = "(84|0[3|5|7|8|9])+(\\d{8})", message = "Invalid phone !!!!!")
    @Column(unique = true)
    private String phone;

    private String address;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @DateTimeFormat
    private LocalDateTime registerDate;

    @JsonIgnore
    private boolean isDeleted;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private int roleID;
    @OneToOne(mappedBy = "user")
    OTPEmail otpEmail;
}
