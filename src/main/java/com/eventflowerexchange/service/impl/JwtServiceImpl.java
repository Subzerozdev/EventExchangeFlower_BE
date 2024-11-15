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
    private final SecretKey secretKey = Keys.hmacShaKeyFor(JwtConstant.SECRET_KEY.getBytes());

    @Override
    public String generateToken(Authentication authentication) {
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        String roles = populateAuthorities(authorities);
        return Jwts.builder()
                .setIssuedAt(new Date())
                .setExpiration(new Date(new Date().getTime() + (1000 * 60 * 60 * 24)))
                .claim("userID", authentication.getName())
                .claim("authorities", roles)
                .signWith(secretKey)
                .compact();
    }

    private String populateAuthorities(Collection<? extends GrantedAuthority> authorities) {
        Set<String> authoritySet = new HashSet<>();
        for (GrantedAuthority authority : authorities) {
            authoritySet.add(authority.getAuthority());
        }
        return String.join(",", authoritySet);
    }

    private Claims extractClaims(String jwtToken) {
        jwtToken = jwtToken.substring(7);
        return Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(jwtToken)
                .getBody();
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
