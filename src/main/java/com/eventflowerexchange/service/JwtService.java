package com.eventflowerexchange.service;

import com.eventflowerexchange.entity.User;
import org.springframework.security.core.Authentication;

public interface JwtService {
    String generateToken(Authentication authentication);
    String getUserIdFromJwtToken(String jwtToken);
    User getUserFromJwtToken(String jwtToken);
}
