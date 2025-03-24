package com.tunehub.service;

import com.tunehub.configuration.LoginResponse;
import com.tunehub.configuration.UsersPrincipal;
import com.tunehub.entity.Users;
import com.tunehub.repository.UserRepository;
import com.tunehub.service.securityservice.JWTService;
import jakarta.transaction.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@Service
public class UsersServiceImpl implements UsersService {

    private final AuthenticationManager authManager;
    private final JWTService jwtService;
    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);
    private final UserRepository userRepository;
    private static final Logger logger = LoggerFactory.getLogger(UsersServiceImpl.class);

    public UsersServiceImpl(AuthenticationManager authManager, JWTService jwtService, UserRepository userRepository) {
        this.authManager = authManager;
        this.jwtService = jwtService;
        this.userRepository = userRepository;
    }

    @Override
    public ResponseEntity<?> validateUser(Users user) { //response
        Authentication auth = authManager.authenticate(new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword()));
        if (auth.isAuthenticated()) {
            return ResponseEntity.ok(new LoginResponse(jwtService.generateToken(user.getEmail()))); // Return token in LoginResponse
        } else {
            return ResponseEntity.status(401).body("Invalid credentials");
        }
    }

    @Override
    @Transactional
    public ResponseEntity<String> addUser(Users user) {
        try {
            if (!userRepository.existsByEmail(user.getEmail())) {
                user.setPassword(encoder.encode(user.getPassword()));
                userRepository.save(user);
                return ResponseEntity.status(HttpStatus.CREATED).body("Registration successful!");
            } else {
                return ResponseEntity.status(HttpStatus.CONFLICT).body("Email already exists!");
            }
        } catch (Exception e) {
            logger.error("Error during user registration", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Registration failed!");
        }
    }

    @Override
    @Transactional
    public ResponseEntity<String> deleteUser(Users user) {
        Long userId = user.getId();
        try {
            if (!userRepository.existsById(userId)) {
                logger.warn("Attempted to delete non-existent user with ID: {}", userId);
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found for deletion.");
            }
            userRepository.deleteById(userId);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            logger.error("Error deleting user with ID: {}", userId, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to delete user.");
        }
    }

    @Override
    @Transactional
    public ResponseEntity<String> updateUser(Users user) {
        try {
            if (!userRepository.existsById(user.getId())) {
                logger.warn("Attempted to update non-existent user with ID: {}", user.getId());
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
            }
            userRepository.save(user);
            return ResponseEntity.ok("User updated successfully");
        } catch (Exception e) {
            logger.error("Error updating user with ID: {}", user.getId(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error updating user");
        }
    }
    public Long getCurrentUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            Object principal = authentication.getPrincipal();
            if (principal instanceof UsersPrincipal) {
                return userRepository.findByEmail(((UsersPrincipal)principal).getUsername()).getId();
            }
        }
        return null;
    }
}