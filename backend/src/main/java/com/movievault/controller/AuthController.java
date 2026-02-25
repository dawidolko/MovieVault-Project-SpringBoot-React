package com.movievault.controller;

import com.movievault.config.JwtConfig;
import com.movievault.dto.RegisterRequestDTO;
import com.movievault.model.User;
import com.movievault.repository.UserRepository;
import com.movievault.security.PasswordHasher;
import com.movievault.service.EmailService;
import com.movievault.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final UserRepository userRepository;
    private final UserService userService;
    private final PasswordHasher passwordHasher;
    private final JwtConfig jwtConfig;
    private final EmailService emailService;

    public AuthController(UserRepository userRepository, UserService userService,
                          PasswordHasher passwordHasher, JwtConfig jwtConfig, EmailService emailService) {
        this.userRepository = userRepository;
        this.userService = userService;
        this.passwordHasher = passwordHasher;
        this.jwtConfig = jwtConfig;
        this.emailService = emailService;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> body) {
        String email = body.get("email");
        String password = body.get("password");

        User user = userRepository.findByEmail(email)
                .orElse(null);

        if (user == null || !passwordHasher.matchPassword(password, user.getPassword())) {
            return ResponseEntity.status(401).body(Map.of("error", "Invalid credentials"));
        }

        String token = jwtConfig.generateToken(user.getEmail(), user.getRole().getName().name());

        Map<String, Object> response = new HashMap<>();
        response.put("token", token);
        response.put("user", userService.toDTO(user));

        return ResponseEntity.ok(response);
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequestDTO dto) {
        try {
            User user = userService.register(dto);
            String token = jwtConfig.generateToken(user.getEmail(), user.getRole().getName().name());

            Map<String, Object> response = new HashMap<>();
            response.put("token", token);
            response.put("user", userService.toDTO(user));

            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    @PostMapping("/password/reset-request")
    public ResponseEntity<?> resetRequest(@RequestBody Map<String, String> body) {
        String email = body.get("email");
        userRepository.findByEmail(email).ifPresent(user -> {
            String token = UUID.randomUUID().toString();
            user.setResetToken(token);
            user.setResetTokenExpiry(LocalDateTime.now().plusHours(1));
            userRepository.save(user);
            try {
                emailService.sendPasswordResetEmail(email, token);
            } catch (Exception ignored) {}
        });
        return ResponseEntity.ok(Map.of("message", "If the email exists, a reset link has been sent"));
    }

    @PostMapping("/password/reset")
    public ResponseEntity<?> resetPassword(@RequestBody Map<String, String> body) {
        String token = body.get("token");
        String newPassword = body.get("newPassword");

        User user = userRepository.findByResetToken(token)
                .orElse(null);

        if (user == null || user.getResetTokenExpiry() == null ||
                user.getResetTokenExpiry().isBefore(LocalDateTime.now())) {
            return ResponseEntity.badRequest().body(Map.of("error", "Invalid or expired token"));
        }

        user.setPassword(passwordHasher.hashPassword(newPassword));
        user.setResetToken(null);
        user.setResetTokenExpiry(null);
        userRepository.save(user);

        return ResponseEntity.ok(Map.of("message", "Password reset successfully"));
    }
}
