package com.tunehub.service;

import com.tunehub.entity.Users;
import com.tunehub.repository.UserRepository;
import com.tunehub.service.securityservice.JWTService;
import jakarta.transaction.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import org.slf4j.Logger; // Import Logger
import org.slf4j.LoggerFactory; // Import LoggerFactory

import java.util.List;

@Service // Spring stereotype annotation to mark this class as a service component
public class UsersServiceImpl implements UsersService {

    private final AuthenticationManager authManager;
    private final JWTService jwtService;
    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);
    private final UserRepository userRepository; // Final for constructor injection - good practice
    private static final Logger logger = LoggerFactory.getLogger(UsersServiceImpl.class); // Explicit Logger declaration

    public UsersServiceImpl(AuthenticationManager authManager, JWTService jwtService, UserRepository userRepository) {
        this.authManager = authManager;
        this.jwtService = jwtService;
        this.userRepository = userRepository;
    }

    @Override
    public Users getUserById(Long id) {
        // Use Optional to handle potential null from findById
        return userRepository.findById(id)
                .orElseThrow(() -> { // Use orElseThrow for cleaner not-found handling
                    logger.warn("User not found with id: {}", id); // Use logger.warn for logging - user not found is not critical error
                    return new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"); // Throw 404 Not Found exception
                });
    }

    @Override
    public Users getUserByName(String name) {
        Users user = userRepository.findByName(name); // Assuming 'findByName' is correctly defined in UserRepository
        if (user == null) {
            logger.warn("User not found with name: {}", name); // Use logger.warn for logging
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"); // Throw 404 Not Found
        }
        return user;
    }

    @Override
    public Users getUserByEmail(String email) {
        Users user = userRepository.findByEmail(email);
        if (user == null) {
            logger.warn("User not found with email: {}", email); // Use logger.warn for logging
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"); // Throw 404 Not Found
        }
        return user;
    }

    @Override
    public String validateUser(Users user) {
        Authentication auth = authManager.authenticate(new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword()));
        if (auth.isAuthenticated()) {
            return jwtService.generateToken(user.getEmail());
        } else {
            return "fail";
        }
    }

    @Override
    public List<Users> getAllUsers() {
        List<Users> users = userRepository.findAll();
        if (users.isEmpty()) {
            logger.info("No users found in database."); // Use logger.info for logging - normal situation, not an error
            return List.of(); // Return empty list instead of null - better for client handling
        }
        return users;
    }

    @Override
    @Transactional // Mark as transactional - important for data consistency
    public ResponseEntity<String> saveUser(Users user) {
        try {
            if (!userRepository.existsByEmail(user.getEmail())) { // Check if email already exists
                user.setPassword(encoder.encode(user.getPassword()));
                userRepository.save(user); // Save the new user to the database
                return ResponseEntity.status(HttpStatus.CREATED).body("Registration successful!"); // Return 201 Created status
            } else {
                return ResponseEntity.status(HttpStatus.CONFLICT).body("Email already exists!"); // Return 409 Conflict status - email already taken
            }
        } catch (Exception e) {
            logger.error("Error during user registration", e); // Use logger.error for logging - error for debugging and monitoring
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Registration failed!"); // Return 500 Internal Server Error for unexpected issues
        }
    }

    @Override
    @Transactional // Mark as transactional - ensures atomicity of delete operation
    public ResponseEntity<String> deleteUser(Long userId) {
        try {
            if (!userRepository.existsById(userId)) { // Check if user exists before deleting
                logger.warn("Attempted to delete non-existent user with ID: {}", userId); // Use logger.warn for logging - attempt to delete non-existent user
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found for deletion."); // Return 404 Not Found if user doesn't exist
            }
            userRepository.deleteById(userId); // Delete user by ID
            return ResponseEntity.noContent().build(); // Return 204 No Content - successful deletion, nobody needed
        } catch (Exception e) {
            logger.error("Error deleting user with ID: {}", userId, e); // Use logger.error for logging - error details including userId and exception
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to delete user."); // Return 500 Internal Server Error for unexpected issues
        }
    }

    @Override
    @Transactional // Mark as transactional - ensures atomicity of update operation
    public ResponseEntity<Users> updateUser(Users user) {
        try {
            if (!userRepository.existsById(user.getId())) { // Check if user exists before updating
                logger.warn("Attempted to update non-existent user with ID: {}", user.getId()); // Use logger.warn for logging - attempt to update non-existent user
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build(); // Return 404 Not Found if user doesn't exist
            }
            Users updatedUser = userRepository.save(user); // Save method updates if entity with ID exists, inserts if not
            return ResponseEntity.ok(updatedUser); // Return 200 OK and the updated user in the response body
        } catch (Exception e) {
            logger.error("Error updating user with ID: {}", user.getId(), e); // Use logger.error for logging - error details including userId and exception
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build(); // Return 500 Internal Server Error for unexpected issues
        }
    }
}