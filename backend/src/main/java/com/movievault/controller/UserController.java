package com.movievault.controller;

import com.movievault.config.JwtConfig;
import com.movievault.model.User;
import com.movievault.repository.UserRepository;
import com.movievault.security.PasswordHasher;
import com.movievault.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserRepository userRepository;
    private final UserService userService;
    private final PasswordHasher passwordHasher;
    private final JwtConfig jwtConfig;

    public UserController(UserRepository userRepository, UserService userService,
                          PasswordHasher passwordHasher, JwtConfig jwtConfig) {
        this.userRepository = userRepository;
        this.userService = userService;
        this.passwordHasher = passwordHasher;
        this.jwtConfig = jwtConfig;
    }

    @GetMapping("/me")
    public ResponseEntity<?> getCurrentUser() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        return userRepository.findByEmail(email)
                .map(user -> ResponseEntity.ok(userService.toDTO(user)))
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getUserById(@PathVariable Long id) {
        return userRepository.findById(id)
                .map(user -> ResponseEntity.ok(userService.toDTO(user)))
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/me")
    public ResponseEntity<?> updateProfile(@RequestBody Map<String, String> body) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByEmail(email).orElse(null);
        if (user == null) return ResponseEntity.notFound().build();

        User updated = userService.updateProfile(user.getId(),
                body.get("firstName"), body.get("lastName"), body.get("bio"));
        return ResponseEntity.ok(userService.toDTO(updated));
    }

    @PutMapping("/{id}/password")
    public ResponseEntity<?> changePassword(@PathVariable Long id, @RequestBody Map<String, String> body) {
        User user = userRepository.findById(id).orElse(null);
        if (user == null) return ResponseEntity.notFound().build();

        if (!passwordHasher.matchPassword(body.get("currentPassword"), user.getPassword())) {
            return ResponseEntity.badRequest().body(Map.of("error", "Current password is incorrect"));
        }

        user.setPassword(passwordHasher.hashPassword(body.get("newPassword")));
        userRepository.save(user);
        return ResponseEntity.ok(Map.of("message", "Password changed"));
    }

    @PutMapping("/{id}/email")
    public ResponseEntity<?> changeEmail(@PathVariable Long id, @RequestBody Map<String, String> body) {
        User user = userRepository.findById(id).orElse(null);
        if (user == null) return ResponseEntity.notFound().build();

        if (!passwordHasher.matchPassword(body.get("password"), user.getPassword())) {
            return ResponseEntity.badRequest().body(Map.of("error", "Password is incorrect"));
        }

        String newEmail = body.get("newEmail");
        if (userRepository.existsByEmailIgnoreCase(newEmail)) {
            return ResponseEntity.badRequest().body(Map.of("error", "Email already in use"));
        }

        user.setEmail(newEmail.toLowerCase());
        userRepository.save(user);

        String newToken = jwtConfig.generateToken(user.getEmail(), user.getRole().getName().name());
        return ResponseEntity.ok(Map.of("token", newToken, "user", userService.toDTO(user)));
    }

    @PostMapping("/{id}/avatar")
    public ResponseEntity<?> uploadAvatar(@PathVariable Long id, @RequestParam("avatar") MultipartFile file) throws IOException {
        User user = userRepository.findById(id).orElse(null);
        if (user == null) return ResponseEntity.notFound().build();

        String uploadsDir = "uploads/avatars/";
        Files.createDirectories(Paths.get(uploadsDir));

        if (user.getAvatarFilename() != null) {
            Files.deleteIfExists(Paths.get(uploadsDir + user.getAvatarFilename()));
        }

        String filename = UUID.randomUUID() + "_" + file.getOriginalFilename();
        Path filePath = Paths.get(uploadsDir + filename);
        Files.write(filePath, file.getBytes());

        user.setAvatarFilename(filename);
        userRepository.save(user);

        return ResponseEntity.ok(Map.of("avatarUrl", user.getAvatarUrl()));
    }

    @DeleteMapping("/{id}/avatar")
    public ResponseEntity<?> deleteAvatar(@PathVariable Long id) throws IOException {
        User user = userRepository.findById(id).orElse(null);
        if (user == null) return ResponseEntity.notFound().build();

        if (user.getAvatarFilename() != null) {
            Files.deleteIfExists(Paths.get("uploads/avatars/" + user.getAvatarFilename()));
            user.setAvatarFilename(null);
            userRepository.save(user);
        }
        return ResponseEntity.ok(Map.of("message", "Avatar deleted"));
    }
}
