package com.eventflowerexchange.dto.response;

import com.eventflowerexchange.dto.OrderInformation;
import com.eventflowerexchange.dto.PostInformation;
import com.eventflowerexchange.entity.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserResponseDTO {
    private String id;
    private float balance;
    private String email;
    private String fullName;
    private String address;
    private String phone;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private boolean isActive;
    private USER_ROLE role;
    private PostInformation postInformation;
    private OrderInformation orderInformation;
}
