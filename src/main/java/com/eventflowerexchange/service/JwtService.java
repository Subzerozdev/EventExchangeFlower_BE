package com.eventflowerexchange.service;

import org.springframework.security.core.Authentication;

public interface JwtService {
    public String generateToken(Authentication authentication);
    public String getUserIdFromJwtToken(String jwtToken);
}
