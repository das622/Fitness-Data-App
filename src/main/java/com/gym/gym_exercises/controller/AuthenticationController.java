package com.gym.gym_exercises.controller;

import com.gym.gym_exercises.dto.AuthenticationResponse;
import com.gym.gym_exercises.dto.LoginRequest;
import com.gym.gym_exercises.model.Role;
import com.gym.gym_exercises.model.User;
import com.gym.gym_exercises.repository.UserRepository;
import com.gym.gym_exercises.security.JwtService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/v1/auth") // The Lobby
public class AuthenticationController {

    private final AuthenticationManager authenticationManager;
    private final UserDetailsService userDetailsService;
    private final JwtService jwtService;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    // Bring in the Database and Password Scrambler tools
    public AuthenticationController(AuthenticationManager authenticationManager,
                                    UserDetailsService userDetailsService,
                                    JwtService jwtService,
                                    UserRepository userRepository,
                                    PasswordEncoder passwordEncoder) {
        this.authenticationManager = authenticationManager;
        this.userDetailsService = userDetailsService;
        this.jwtService = jwtService;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    // 1. THE NEW REGISTRATION DOOR
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody User request) {
        try {
            System.out.println("--- ATTEMPTING REGISTRATION FOR: " + request.getEmail() + " ---");
            System.out.println("RAW PASSWORD RECEIVED FROM FRONTEND: [" + request.getPassword() + "]");

            // Check if password is null or empty before encrypting
            if (request.getPassword() == null || request.getPassword().isEmpty()) {
                throw new IllegalArgumentException("Password cannot be empty!");
            }

            request.setPassword(passwordEncoder.encode(request.getPassword()));
            request.setRole(Role.USER);
            userRepository.save(request);

            System.out.println("--- REGISTRATION SUCCESSFUL! ---");

            // BYPASS: Explicitly cast the request to UserDetails to satisfy IntelliJ
            String jwtToken = jwtService.generateToken((UserDetails) request);

            return ResponseEntity.ok(new AuthenticationResponse(
                    jwtToken,
                    request.getId(),
                    request.getFirstName(),
                    request.getEmail()
            ));
        } catch (Exception e) {
            System.out.println("!!! REGISTRATION FAILED !!!");
            System.out.println("REASON: " + e.getMessage());
            return ResponseEntity.status(400).body("Registration Failed: " + e.getMessage());
        }
    }
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        try {
            System.out.println("--- ATTEMPTING LOGIN FOR: " + request.getEmail() + " ---");

            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
            );

            System.out.println("--- LOGIN SUCCESSFUL! GENERATING TOKEN ---");

            UserDetails userDetails = userDetailsService.loadUserByUsername(request.getEmail());
            String jwtToken = jwtService.generateToken(userDetails);
            User user = (User) userDetails;

            return ResponseEntity.ok(new AuthenticationResponse(
                    jwtToken,
                    user.getId(),
                    user.getFirstName(),
                    user.getEmail()
            ));

        } catch (Exception e) {
            System.out.println("!!! LOGIN FAILED !!!");
            System.out.println("REASON: " + e.getMessage());
            e.printStackTrace(); // This prints the exact line number of the crash
            return ResponseEntity.status(403).body("Login Failed: " + e.getMessage());
        }
    }
    // 2. THE EXISTING LOGIN DOOR
//    @PostMapping("/login")
//    public ResponseEntity<AuthenticationResponse> login(@RequestBody LoginRequest request) {
//        authenticationManager.authenticate(
//                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
//        );
//
//        // BYPASS: Fetch it as a raw UserDetails object first
//        UserDetails userDetails = userDetailsService.loadUserByUsername(request.getEmail());
//
//        // Hand the raw UserDetails to the printer (IntelliJ will love this)
//        String jwtToken = jwtService.generateToken(userDetails);
//
//        // Now cast it to your custom User so we can grab the ID and Name
//        User user = (User) userDetails;
//
//        return ResponseEntity.ok(new AuthenticationResponse(
//                jwtToken,
//                user.getId(),
//                user.getFirstName(),
//                user.getEmail()
//        ));
//    }

}