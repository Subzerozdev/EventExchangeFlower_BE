package com.eventflowerexchange.config;

import com.eventflowerexchange.util.JwtConstant;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.crypto.SecretKey;
import java.io.IOException;
import java.util.List;

public class JwtTokenValidator extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        // Get Jwt Token from Request Header
        String jwt = request.getHeader(JwtConstant.JWT_HEADER);
        // Check if Jwt is existed and is validated
        if (jwt != null) {
            // Get payload
            jwt = jwt.substring(7);
            try {
                // Get encode key
                SecretKey secretKey = Keys.hmacShaKeyFor(JwtConstant.SECRET_KEY.getBytes());
                // Get payload (claims) from Jwt Token
                Claims claims = Jwts.parserBuilder().setSigningKey(secretKey).build().parseClaimsJws(jwt).getBody();
                // Get userID
                String userID = String.valueOf(claims.get("userID"));
                // Get role / permission
                String authorities = String.valueOf(claims.get("authorities"));

                List<GrantedAuthority> auth = AuthorityUtils.commaSeparatedStringToAuthorityList(authorities);

                Authentication authentication = new UsernamePasswordAuthenticationToken(userID, null, auth);

                SecurityContextHolder.getContext().setAuthentication(authentication);

            } catch (ExpiredJwtException exception) {
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "The token is not valid.");
                return;
            } catch (Exception e) {
                System.out.println(" Some other exception in JWT parsing ");
                return;
            }
        }
        filterChain.doFilter(request, response);
    }
}
