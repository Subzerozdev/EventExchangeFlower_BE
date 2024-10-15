package com.eventflowerexchange.service.impl;

import com.eventflowerexchange.entity.USER_ROLE;
import com.eventflowerexchange.entity.User;
import com.eventflowerexchange.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        // Get user by email
        User user = userRepository.findUserByEmail(email);
        // Check if user is null
        if (user == null || !user.isActive()) {
            throw new UsernameNotFoundException("User is not found with userID: " + email);
        }
        // Get user role
        USER_ROLE role = user.getRole();
        // Add role / permission to authorities
        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(role.toString()));
        // Return
        return new org.springframework.security.core.userdetails.User(user.getId(), user.getPassword(), authorities);
    }
}
