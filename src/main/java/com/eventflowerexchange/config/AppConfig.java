package com.eventflowerexchange.config;

import com.eventflowerexchange.entity.USER_ROLE;
import com.eventflowerexchange.entity.User;
import com.eventflowerexchange.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.AuthorizeHttpRequestsDsl;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;

@Configuration
@EnableWebSecurity
public class AppConfig {
    @Autowired
    private UserRepository userRepository;
    @Bean // Mark this method of Bean to be managed in Spring IoC Container and be hold all by App Context
        /* SecurityFilterChain (request will be here first):
         * Request Filtering
         * Authentication (By Token)
         * Authorization (By Role)
         * Custom Security and Modify Filter
         */
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        // Config http requests
        http.sessionManagement(management ->
                        // Strict option: App or Spring Security won’t create and use any session at all.
                        // -> Not use cookies and server-side storage
                        management.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                // Define rules for endpoints
                .authorizeHttpRequests(Authorize -> Authorize
                        // Only user with below roles can access the path
                        .requestMatchers("/api/admin/**").hasRole("ADMIN")
                        .requestMatchers("/api/seller/**").hasRole("SELLER")
                        // Only user have logged in can access the path
                        .requestMatchers("/api/**").authenticated()
                        // Allow any request to another path
                        .anyRequest().permitAll())
                // Add custom Filter before BasicAuthenticationFilter
                .addFilterBefore(new JwtTokenValidator(), BasicAuthenticationFilter.class)
                // Disabling CSRF Protection
                .csrf(csrf -> csrf.disable())
                // Enabling CORS
                .cors(cors -> cors.configurationSource(corsConfigurationSource()));
        return http.build();
    }

    private CorsConfigurationSource corsConfigurationSource() {
        return new CorsConfigurationSource() {
            @Override
            public CorsConfiguration getCorsConfiguration(HttpServletRequest arg0) {
                CorsConfiguration cfg = new CorsConfiguration();
                cfg.setAllowedOrigins(Arrays.asList(
                        "https://swp-391-14qu-osi30s-projects.vercel.app",
                        "https://swp-391-14qu.vercel.app",
                        "http://localhost:5173"));
                cfg.setAllowedMethods(Collections.singletonList("*"));
                cfg.setAllowCredentials(true);
                cfg.setAllowedHeaders(Collections.singletonList("*"));
                cfg.setExposedHeaders(Arrays.asList("Authorization"));
                cfg.setMaxAge(3600L);
                return cfg;
            }
        };
    }

    @Bean
    ApplicationRunner applicationRunner(){
        return args -> {
            if (userRepository.findUserByEmail("hoaloicuofficial@gmail.com")==null){
                User user = User.builder()
                        .email("hoaloicuofficial@gmail.com")
                        .password(passwordEncoder().encode("12345678"))
                        .isActive(true)
                        .fullName("Admin")
                        .createdAt(LocalDateTime.now())
                        .role(USER_ROLE.ROLE_ADMIN)
                        .build();
                userRepository.save(user);
            }
        };
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
