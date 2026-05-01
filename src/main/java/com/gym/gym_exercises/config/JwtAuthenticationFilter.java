package com.gym.gym_exercises.config;

import com.gym.gym_exercises.security.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.jspecify.annotations.NonNull;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import java.util.Arrays;

import java.io.IOException;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;

    public JwtAuthenticationFilter(JwtService jwtService, UserDetailsService userDetailsService){
        this.jwtService = jwtService;
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain
    ) throws ServletException, IOException{
        // 1. THE PAT DOWN: Look for the Authorization header
        final String authHeader = request.getHeader("Authorization");
        final String jwt;
        final String userEmail;

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        jwt = authHeader.substring(7);

        try {
            userEmail = jwtService.extractUsername(jwt);
        } catch (Exception e) {
            // Bad/expired token — just let the request proceed unauthenticated
            filterChain.doFilter(request, response);
            return;
        }
    // 5. THE VIP CHECK: If we found an email, and they aren't authenticated yet...
        if (userEmail != null && SecurityContextHolder.getContext().getAuthentication() == null) {

        // Go to the database and get the User details
        UserDetails userDetails = this.userDetailsService.loadUserByUsername(userEmail);

        // 6. THE MATH: Ask JwtService to verify the signature
        if (jwtService.isTokenValid(jwt, userDetails)) {

            // 7. CREATE TICKET: The token is authentic. Create the Spring Security ticket.
            UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                    userDetails,
                    null,
                    userDetails.getAuthorities()
            );

            authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

            // 8. OPEN VIP LOUNGE: Make the user officially authenticated for this request
            SecurityContextHolder.getContext().setAuthentication(authToken);
        }
    }

    // 9. THE FINAL DOOR: Let the request proceed to your controllers
        filterChain.doFilter(request, response);
}

}
