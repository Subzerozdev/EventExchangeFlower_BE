package com.eventflowerexchange.service.impl;

import com.eventflowerexchange.dto.request.AuthRequestDTO;
import com.eventflowerexchange.dto.request.LoginRequestDTO;
import com.eventflowerexchange.dto.request.UpdateRequestDTO;
import com.eventflowerexchange.dto.request.UserRequestDTO;
import com.eventflowerexchange.dto.response.AuthResponseDTO;
import com.eventflowerexchange.entity.USER_ROLE;
import com.eventflowerexchange.entity.User;
import com.eventflowerexchange.exception.DuplicateEntity;
import com.eventflowerexchange.mapper.UserMapper;
import com.eventflowerexchange.repository.UserRepository;
import com.eventflowerexchange.service.JwtService;
import com.eventflowerexchange.service.UserService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private JwtService jwtService;
    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    @Override
    public String register(UserRequestDTO userRequestDTO) {
        // Check if phone or email is existed
        if (userRepository.existsByPhone(userRequestDTO.getPhone())
                || userRepository.existsByEmail(userRequestDTO.getEmail())) {
            throw new DuplicateEntity("Duplicate phone or email!");
        }
        User user = userMapper.toUser(userRequestDTO);
        // Encode password
        user.setPassword(passwordEncoder.encode(userRequestDTO.getPassword()));
        // Set other fields
        user.setCreatedAt(LocalDateTime.now());
        user.setRole(USER_ROLE.ROLE_CUSTOMER);
        // Save account to DB
        userRepository.save(user);
        // Return userID
        return user.getId();
    }

    @Override
    public AuthResponseDTO login(AuthRequestDTO authRequestDTO){
        //
        Authentication authentication = authenticate(authRequestDTO.getEmail(), authRequestDTO.getPassword());
        // Generate Jwt Token
        String jwt = jwtService.generateToken(authentication);
        // Get user by Email
        User user = userRepository.findUserByEmail(authRequestDTO.getEmail());
        // Setup and return Auth Response
        return getAuthResponse(jwt, user);
    }

    @Override
    public User findUserById(String userID) {
        return userRepository.findUserById(userID);
    }

    @Override
    public User updateUserById(String userID, UpdateRequestDTO updateRequestDTO) {
        User userUpdate = userRepository.findUserById(userID);
        userMapper.updateUser(userUpdate, updateRequestDTO);
        userUpdate.setUpdatedAt(LocalDateTime.now());
        return userRepository.save(userUpdate);
    }

    private AuthResponseDTO getAuthResponse(String jwtToken, User user){
        return AuthResponseDTO.builder()
                .jwtToken(jwtToken)
                .user(user)
                .build();
    }

    private Authentication authenticate(String email, String password){
        UserDetails userDetails = customUserDetailsService.loadUserByUsername(email);
        if (userDetails == null) {
            throw new BadCredentialsException("Invalid email...");
        }
        if (!passwordEncoder.matches(password, userDetails.getPassword())) {
            throw new BadCredentialsException("Invalid password...");
        }
        return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
    }
}

