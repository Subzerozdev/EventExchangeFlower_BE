package com.eventflowerexchange.service.impl;

import com.eventflowerexchange.entity.User;
import com.eventflowerexchange.repository.UserRepository;
import com.eventflowerexchange.service.JwtService;
import com.eventflowerexchange.util.JwtConstant;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class JwtServiceImpl implements JwtService {
    private final UserRepository userRepository;
    // *1 JwtConstant.SECRET_KEY.getBytes(): Encodes String (SECRET_KEY) into bytes arr
    // *2 Keys.hmacShaKeyFor(): Use algorithm HmacSHA384 to encode bytes arr
    private final SecretKey secretKey = Keys.hmacShaKeyFor(JwtConstant.SECRET_KEY.getBytes());

    @Override
    public String generateToken(Authentication authentication) {
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        String roles = populateAuthorities(authorities);
        return Jwts.builder() //Create instance jwt
                .setIssuedAt(new Date()) // Set start time
                .setExpiration(new Date(new Date().getTime() + (1000 * 10))) // Set end time (one day)
                .claim("userID", authentication.getName()) // Add new claim "userID" with value username/email
                .claim("authorities", roles) // Add new claim "authorities" with value role/permission
                .signWith(secretKey) // Sign key using algorithm HmacSHA384
                .compact(); // Compact JWT to single String
    }

    // Convert authorities to String with format "auth1,auth2,auth3"
    private String populateAuthorities(Collection<? extends GrantedAuthority> authorities) {
        // Create HashSet - every item is unique
        Set<String> authoritySet = new HashSet<>();
        // Add each authority in authorities to HashSet
        for (GrantedAuthority authority : authorities) {
            authoritySet.add(authority.getAuthority());
        }
        // Join each authority name to String
        return String.join(",", authoritySet);
    }

    private Claims extractClaims(String jwtToken) {
        // Get payload (data) from Jwt Token
        jwtToken = jwtToken.substring(7);
        // Parse and validate Jwt Token
        return Jwts.parserBuilder() // Create instance
                .setSigningKey(secretKey) // Set key used to sign the JWT for verifying token
                .build() // Build based on the configuration settings
                .parseClaimsJws(jwtToken) // Return Jws<Claims> if successful verifying JWT Token
                .getBody(); // Extracts the Claims Obj from Jws<Claims>
    }

    @Override
    public String getUserIdFromJwtToken(String jwtToken) {
        return String.valueOf(extractClaims(jwtToken).get("userID"));
    }

    @Override
    public User getUserFromJwtToken(String jwtToken) {
        String userID = getUserIdFromJwtToken(jwtToken);
        return userRepository.findUserById(userID);
    }
}
