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
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        // The VIP List: Allow Vercel and your local laptop
        configuration.setAllowedOrigins(Arrays.asList("https://davidlifts.fit", "http://localhost:5173"));
        // Allow all the standard HTTP methods
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        configuration.setAllowedHeaders(Arrays.asList("Authorization", "Content-Type"));
        configuration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration); // Apply to all endpoints
        return source;
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

    // 2. THE VISUAL CHECK: Does it exist and start with "Bearer "?
    if (authHeader == null || !authHeader.startsWith("Bearer ")) {
        filterChain.doFilter(request, response); // Move along, no token here
        return;
    }
    // 3. STRIP THE PREFIX: Grab the raw xx.yy.zz token
    jwt = authHeader.substring(7);

    // 4. THE DECODE: Ask JwtService to read the email from the payload
    userEmail = jwtService.extractUsername(jwt);
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
