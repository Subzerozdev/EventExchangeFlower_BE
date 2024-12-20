package com.eventflowerexchange.service.impl;

import com.eventflowerexchange.dto.request.AuthRequestDTO;
import com.eventflowerexchange.dto.request.UpdateRequestDTO;
import com.eventflowerexchange.dto.request.UserRequestDTO;
import com.eventflowerexchange.dto.response.AuthResponseDTO;
import com.eventflowerexchange.entity.*;
import com.eventflowerexchange.exception.DuplicateEntity;
import com.eventflowerexchange.mapper.UserMapper;
import com.eventflowerexchange.repository.UserRepository;
import com.eventflowerexchange.service.JwtService;
import com.eventflowerexchange.service.UserService;
import com.eventflowerexchange.util.FieldValidation;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final CustomUserDetailsService customUserDetailsService;

    @Override
    public String register(UserRequestDTO userRequestDTO) {
        if (userRepository.existsByPhone(userRequestDTO.getPhone())
                || userRepository.existsByEmail(userRequestDTO.getEmail())) {
            throw new DuplicateEntity("Duplicate phone or email!");
        }
        User user = userMapper.toUser(userRequestDTO);
        user.setPassword(passwordEncoder.encode(userRequestDTO.getPassword()));
        user.setCreatedAt(LocalDateTime.now());
        user.setRole(USER_ROLE.ROLE_CUSTOMER);
        userRepository.save(user);
        return user.getId();
    }

    @Override
    public AuthResponseDTO login(AuthRequestDTO authRequestDTO) {
        Authentication authentication = authenticate(authRequestDTO.getEmail(), authRequestDTO.getPassword());
        String jwt = jwtService.generateToken(authentication);
        User user = userRepository.findUserByEmail(authRequestDTO.getEmail());
        return getAuthResponse(jwt, user);
    }

    @Override
    public User findUserById(String userID) {
        return userRepository.findUserById(userID);
    }

    @Override
    public void updatePasswordById(String userID, String password) {
        User user = userRepository.findUserById(userID);
        user.setPassword(passwordEncoder.encode(password));
        userRepository.save(user);
    }

    @Override
    public String getUserIdByEmail(String email) {
        User user = userRepository.findUserByEmail(email);
        return user.getId();
    }

    @Override
    public void updateRole(User user) {
        user.setRole(USER_ROLE.ROLE_SELLER);
        userRepository.save(user);
    }

    @Override
    public AuthResponseDTO updateSellerToken(User user) {
        UserDetails userDetails = customUserDetailsService.loadUserByUsername(user.getEmail());
        Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
        String jwt = jwtService.generateToken(authentication);
        return getAuthResponse(jwt, null);
    }

    @Override
    public void deleteUserById(String userID) {
        User user = userRepository.findUserById(userID);
        FieldValidation.checkObjectExist(user, "user");
        if (!user.getRole().equals(USER_ROLE.ROLE_ADMIN)){
            user.setActive(false);
        }
        userRepository.save(user);
    }

    @Override
    public List<User> getUsers() {
        return userRepository.findAll();
    }

    @Override
    public User updateUserById(String userID, UpdateRequestDTO updateRequestDTO) {
        User userUpdate = userRepository.findUserById(userID);
        userMapper.updateUser(userUpdate, updateRequestDTO);
        userUpdate.setUpdatedAt(LocalDateTime.now());
        return userRepository.save(userUpdate);
    }

    private AuthResponseDTO getAuthResponse(String jwtToken, User user) {
        return AuthResponseDTO.builder()
                .jwtToken(jwtToken)
                .user(user)
                .build();
    }

    private Authentication authenticate(String email, String password) {
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

