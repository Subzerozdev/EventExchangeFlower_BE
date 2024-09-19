package com.eventflowerexchange.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.Date;


@Data
@Entity

public class User {
    @JsonIgnore
@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
 private Long userID;

@NotBlank(message = "Email can not  be blank !!!!")
@Email(message = " Invalid Email !!!! ")
@Column(unique = true)
private String email;

@NotBlank(message = "Password can not  be blank !!!!")
@Size(min =3, message = "Password must be  at least  6 character !!!!!")
private String password;






@NotBlank(message = "fullName can not  be blank !!!!")
private String fullName;

@Pattern(regexp = "(84|0[3|5|7|8|9])+(\\d{8})" , message = "Invalid phone !!!!!")
@Column(unique = true)
private String phone;

@NotBlank(message = "  Address can not  be blank !!!!")
private String address;

    @JsonIgnore
@DateTimeFormat
private LocalDateTime registerDate;
    @JsonIgnore
private int roleID;



}
