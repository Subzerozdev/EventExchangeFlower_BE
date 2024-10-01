package com.eventflowerexchange.service.impl;

import com.eventflowerexchange.service.JwtService;
import com.eventflowerexchange.util.JwtConstant;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;

@Service
public class JwtServiceImpl implements JwtService {
    // *1 JwtConstant.SECRET_KEY.getBytes(): Encodes String (SECRET_KEY) into bytes arr
    // *2 Keys.hmacShaKeyFor(): Use algorithm HmacSHA384 to encode bytes arr
    private final SecretKey secretKey = Keys.hmacShaKeyFor(JwtConstant.SECRET_KEY.getBytes());

    @Override
    public String generateToken(Authentication authentication) {
        String jwt = Jwts.builder() //Create instance jwt
                .setIssuedAt(new Date()) // Set start time
                .setExpiration(new Date(new Date().getTime() + (1000 * 60 * 60 * 24))) // Set end time (one day)
                .claim("userID", authentication.getName()) // Add new claim "userID" with value username/email
                .claim("authorities", authentication.getAuthorities().toString()) // Add new claim "authorities" with value role/permission
                .signWith(secretKey) // Sign key using algorithm HmacSHA384
                .compact(); // Compact JWT to single String
        return jwt;
    }

    @Override
    public String getUserIdFromJwtToken(String jwtToken) {
        // Get payload (data) from Jwt Token
        jwtToken = jwtToken.substring(7);
        // Parse and validate Jwt Token
        Claims claims = Jwts.parserBuilder() // Create instance
                .setSigningKey(secretKey) // Set key used to sign the JWT for verifying token
                .build() // Build based on the configuration settings
                .parseClaimsJws(jwtToken) // Return Jws<Claims> if successful verifying JWT Token
                .getBody(); // Extracts the Claims Obj from Jws<Claims>
        return String.valueOf(claims.get("userID"));
    }
}
