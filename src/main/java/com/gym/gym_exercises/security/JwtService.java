package com.gym.gym_exercises.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtService {
    // get Secret Key from enviroment variables
    @Value("${application.security.jwt.secret-key}")
    private String secretKey;

    @Value("${application.security.jwt.expiration}")
    private long jwtExpiration;

    // JOB 1, Below is where we print the token, xx.yy.zz, xx = the header, yy = payload, zz = signature
    public String generateToken(UserDetails userDetails){
        return generateToken(new HashMap<>(), userDetails);
    }
    public String generateToken(Map<String, Object> extraClaims, UserDetails userDetails){
        return Jwts.builder()
                .claims(extraClaims) // yy: Extra data (like roles)
                .subject(userDetails.getUsername()) // yy: The User's Email
                .issuedAt(new Date(System.currentTimeMillis())) // yy: Time created
                .expiration(new Date(System.currentTimeMillis() + jwtExpiration)) // yy: Time it dies
                .signWith(getSignInKey()) // THE MATH: xx + yy + Secret Key = zz
                .compact();
    }
    // JOB 2: THE READER (Extracting Data from Token)
    public String extractUsername(String token){
        return extractClaim(token, Claims::getSubject);
    }
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }
    private Claims extractAllClaims(String token) {
        return Jwts.parser()
                .verifyWith(getSignInKey()) // The server verifies the signature before reading!
                .build()
                .parseSignedClaims(token)
                .getPayload(); // Returns the yy portion
    }
    // JOB 3: THE VERIFIER, Checking if it's legit, and matches
    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        // Checks if the email matches AND if the token hasn't expired yet
        return (username.equals(userDetails.getUsername())) && !isTokenExpired(token);
    }
    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }
    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }
    // HELPER: The Secret Key Formatter
    private javax.crypto.SecretKey getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
