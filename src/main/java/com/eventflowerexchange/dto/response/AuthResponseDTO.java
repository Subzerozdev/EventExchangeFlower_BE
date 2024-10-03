package com.eventflowerexchange.dto.response;

import com.eventflowerexchange.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AuthResponseDTO {
    private String jwtToken;
    private User user;
}
